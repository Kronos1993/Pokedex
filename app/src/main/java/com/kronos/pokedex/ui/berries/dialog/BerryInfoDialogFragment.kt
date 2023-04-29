package com.kronos.pokedex.ui.berries.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kronos.core.extensions.binding.fragmentBinding
import com.kronos.pokedex.R
import com.kronos.pokedex.databinding.FragmentDialogBerryInfoBindingImpl
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.item.BerryInfo
import com.kronos.pokedex.ui.berries.list.CURRENT_BERRY
import com.kronos.pokedex.ui.items.list.CURRENT_ITEM
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ref.WeakReference

@AndroidEntryPoint
class BerryInfoDialogFragment : BottomSheetDialogFragment() {
    private val binding by fragmentBinding<FragmentDialogBerryInfoBindingImpl>(R.layout.fragment_dialog_berry_info)
    private val viewModel by viewModels<BerryInfoViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.run {
        viewModel = this@BerryInfoDialogFragment.viewModel
        lifecycleOwner = this@BerryInfoDialogFragment.viewLifecycleOwner
        setListeners()
        setObservers()
        root
    }

    private fun setListeners() {

    }

    private fun setObservers() {
        viewModel.berryInfo.observe(this.viewLifecycleOwner, ::handleBerryInfo)
        binding.textViewSeeMore.setOnClickListener{
            val bundle = Bundle()
            bundle.putSerializable(CURRENT_ITEM, viewModel.berryInfo.value?.itemResource)
            findNavController().navigate(R.id.action_nav_detail_berry_dialog_to_nav_item_detail, bundle)
        }
    }

    private fun handleBerryInfo(berryInfo: BerryInfo) {
        Glide.with(requireContext()).load(viewModel.urlProvider.getItemImageUrl(berryInfo.itemResource.name)).placeholder(
            R.drawable.ic_berries).into(binding.imageViewBerry)
        viewModel.berryFlavorListAdapter.get()?.submitList(berryInfo.flavors)
        viewModel.berryFlavorListAdapter.get()?.notifyDataSetChanged()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpDialog()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(true)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    private fun setUpDialog() {
        this.isCancelable = true
        val bundle = arguments
        if (bundle?.get(CURRENT_BERRY) != null) {
            viewModel.loadBerryInfo((bundle.get(CURRENT_BERRY) as NamedResourceApi))
            initView()
        } else {
            hideDialog()
        }

    }

    private fun initView() {
        binding.recyclerViewBerryFlavors.layoutManager =
            GridLayoutManager(context, 2)
        binding.recyclerViewBerryFlavors.setHasFixedSize(false)
        if (viewModel.berryFlavorListAdapter.get() == null)
            viewModel.berryFlavorListAdapter = WeakReference(BerryFlavorListAdapter())
        viewModel.berryFlavorListAdapter.get()?.setUrlProvider(viewModel.urlProvider)
        binding.recyclerViewBerryFlavors.adapter =
            viewModel.berryFlavorListAdapter.get()
    }

    private fun hideDialog() {
        lifecycleScope.launch {
            withContext(Dispatchers.Main) {
                findNavController().navigateUp()
            }
        }
    }

    override fun onDestroy() {
        binding.unbind()
        super.onDestroy()
    }

    override fun onPause() {
        binding.unbind()
        viewModel.postBerryInfo(BerryInfo())
        super.onPause()
    }
}