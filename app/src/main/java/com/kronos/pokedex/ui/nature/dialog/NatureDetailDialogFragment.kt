package com.kronos.pokedex.ui.nature.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kronos.core.extensions.binding.fragmentBinding
import com.kronos.pokedex.R
import com.kronos.pokedex.databinding.FragmentDialogNatureInfoBinding
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.nature.NatureDetail
import com.kronos.pokedex.ui.move.list.CURRENT_MOVE
import com.kronos.pokedex.ui.nature.list.CURRENT_NATURE
import com.kronos.pokedex.ui.pokemon.detail.PokemonDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class NatureDetailDialogFragment : BottomSheetDialogFragment() {
    private val binding by fragmentBinding<FragmentDialogNatureInfoBinding>(R.layout.fragment_dialog_nature_info)
    private val viewModel by viewModels<NatureDetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.run {
        viewModel = this@NatureDetailDialogFragment.viewModel
        lifecycleOwner = this@NatureDetailDialogFragment.viewLifecycleOwner
        setListeners()
        setObservers()
        root
    }

    private fun setListeners() {

    }

    private fun setObservers() {
        viewModel.natureInfo.observe(this.viewLifecycleOwner, ::handleNatureDetail)
    }

    private fun handleNatureDetail(natureDetail: NatureDetail) {
        if(!natureDetail.name.isNullOrEmpty()){
            viewModel.getNatureName(natureDetail)
            initView()
        }else{
            hideDialog()
        }
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
        if (bundle?.get(CURRENT_NATURE) != null) {
            if ((bundle.get(CURRENT_NATURE) is NamedResourceApi))
                viewModel.loadNatureInfo((bundle.get(CURRENT_NATURE) as NamedResourceApi))
            else
                viewModel.postNatureInfo((bundle.get(CURRENT_NATURE) as NatureDetail))
        } else {
            hideDialog()
        }
    }

    private fun initView() {
    }

    private fun hideDialog() {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                findNavController().navigateUp()
            }
        }
    }

    override fun onDestroy() {
        binding.unbind()
        viewModel.postNatureInfo(NatureDetail())
        super.onDestroy()
    }
}