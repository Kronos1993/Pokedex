package com.kronos.pokedex.ui.pokedex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kronos.core.adapters.AdapterItemClickListener
import com.kronos.core.extensions.binding.fragmentBinding
import com.kronos.core.util.LoadingDialog
import com.kronos.core.util.show
import com.kronos.pokedex.R
import com.kronos.pokedex.databinding.FragmentPokedexBinding
import com.kronos.pokedex.domian.model.ResponseListItem
import com.kronos.pokedex.ui.ItemListAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.lang.ref.WeakReference
import java.util.*

const val CURRENT_POKEDEX = "current_pokedex"

@AndroidEntryPoint
class PokedexFragment : Fragment() {
    private val binding by fragmentBinding<FragmentPokedexBinding>(R.layout.fragment_pokedex)

    private val viewModel by viewModels<PokedexViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.run {
        viewModel = this@PokedexFragment.viewModel
        lifecycleOwner = this@PokedexFragment.viewLifecycleOwner
        root
    }

    override fun onResume() {
        super.onResume()
        initViewModel()
        initViews()
        setListeners()
        observeViewModel()
    }

    private fun setListeners() {
    }

    private fun observeViewModel() {
        viewModel.pokedexList.observe(this.viewLifecycleOwner, ::handlePokedexList)
        viewModel.loading.observe(this.viewLifecycleOwner, ::handleLoading)
        viewModel.error.observe(this.viewLifecycleOwner, ::handleError)
    }


    private fun handleError(hashtable: Hashtable<String, String>) {
        if (hashtable["error"] != null) {
            if (hashtable["error"]!!.isNotEmpty()) {
                show(
                    binding.recyclerViewPokedexList,
                    hashtable["error"].orEmpty(),
                    com.kronos.resources.R.color.snack_bar_white,
                    com.kronos.resources.R.color.snack_bar_error_background
                )
            } else {
                show(
                    binding.recyclerViewPokedexList,
                    hashtable["error"].orEmpty(),
                    com.kronos.resources.R.color.snack_bar_white,
                    com.kronos.resources.R.color.snack_bar_success_background
                )
            }
        }
    }

    private fun handleLoading(b: Boolean) {
        if (b) {
            LoadingDialog.getProgressDialog(
                requireContext(),
                R.string.loading_dialog_text,
                com.kronos.resources.R.color.colorSecondaryVariant
            )!!.show()
        } else {
            LoadingDialog.getProgressDialog(
                requireContext(),
                R.string.loading_dialog_text,
                com.kronos.resources.R.color.colorSecondaryVariant
            )!!.dismiss()
        }
    }

    private fun handlePokedexList(list: List<ResponseListItem>) {
        viewModel.pokedexListAdapter.get()?.submitList(list)
        viewModel.pokedexListAdapter.get()?.notifyDataSetChanged()
    }

    private fun initViews() {
        binding.recyclerViewPokedexList.layoutManager = GridLayoutManager(context,2)
        binding.recyclerViewPokedexList.setHasFixedSize(false)
        if (viewModel.pokedexListAdapter.get() == null)
            viewModel.pokedexListAdapter = WeakReference(ItemListAdapter())
        binding.recyclerViewPokedexList.adapter = viewModel.pokedexListAdapter.get()
        viewModel.pokedexListAdapter.get()?.setAdapterItemClick(object :
            AdapterItemClickListener<ResponseListItem> {
            override fun onItemClick(t: ResponseListItem, pos: Int) {
                val bundle = Bundle()
                bundle.putSerializable(CURRENT_POKEDEX, t)
                findNavController().navigate(R.id.action_nav_pokedex_to_nav_pokemon_list, bundle)
            }
        })

        binding.recyclerViewPokedexList.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount: Int = (recyclerView.layoutManager as GridLayoutManager).childCount
                val totalItemCount: Int = (recyclerView.layoutManager as GridLayoutManager).itemCount
                val firstVisibleItemPosition: Int = (recyclerView.layoutManager as GridLayoutManager).findFirstVisibleItemPosition()
                if (!viewModel.loading.value!!) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0)
                        viewModel.getMorePokedex()
                }
            }
        })
    }

    private fun initViewModel() {
        viewModel.setLimit(viewModel.limit.value.let{ it ?: resources.getInteger(R.integer.def_limit)})
        viewModel.setOffset(viewModel.offset.value.let{ it ?: resources.getInteger(R.integer.def_offset)})
        if (viewModel.pokedexList.value.isNullOrEmpty())
            viewModel.getPokedex()
    }

    override fun onDestroyView() {
        binding.unbind()
        super.onDestroyView()
    }

    override fun onPause() {
        binding.unbind()
        super.onPause()
    }


}