package com.kronos.pokedex.ui.egg_group.list

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.kronos.core.adapters.AdapterItemClickListener
import com.kronos.core.extensions.binding.fragmentBinding
import com.kronos.core.util.LoadingDialog
import com.kronos.core.util.show
import com.kronos.pokedex.R
import com.kronos.pokedex.databinding.FragmentEggGroupsBinding
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.ui.pokemon.detail.adapter.PokemonEggGroupAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.lang.ref.WeakReference
import java.util.*

const val CURRENT_EGG_GROUP = "current_egg_group"

@AndroidEntryPoint
class EggGroupsFragment : Fragment() {
    private val binding by fragmentBinding<FragmentEggGroupsBinding>(R.layout.fragment_egg_groups)

    private val viewModel by viewModels<EggGroupsViewModel>()

    lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.run {
        viewModel = this@EggGroupsFragment.viewModel
        lifecycleOwner = this@EggGroupsFragment.viewLifecycleOwner
        setHasOptionsMenu(true)
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
        viewModel.eggGroupList.observe(this.viewLifecycleOwner, ::handleItems)
        viewModel.loading.observe(this.viewLifecycleOwner, ::handleLoading)
        viewModel.error.observe(this.viewLifecycleOwner, ::handleError)
    }


    private fun handleError(hashtable: Hashtable<String, String>) {
        if (hashtable["error"] != null) {
            if (hashtable["error"]!!.isNotEmpty()) {
                show(
                    binding.textNoEggGroups,
                    hashtable["error"].orEmpty(),
                    com.kronos.resources.R.color.snack_bar_white,
                    com.kronos.resources.R.color.snack_bar_error_background
                )
            } else {
                show(
                    binding.textNoEggGroups,
                    hashtable["error"].orEmpty(),
                    com.kronos.resources.R.color.snack_bar_white,
                    com.kronos.resources.R.color.snack_bar_success_background
                )
            }
        }
    }

    private fun handleLoading(b: Boolean) {
        try {
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
        } catch (e: Exception) {
        }
    }

    private fun handleItems(list: List<NamedResourceApi>) {
        viewModel.eggGroupAdapter.get()?.submitList(list)
        viewModel.eggGroupAdapter.get()?.notifyDataSetChanged()
    }

    private fun initViews() {
        binding.recyclerViewEggGroupList.layoutManager = GridLayoutManager(context, 2)
        binding.recyclerViewEggGroupList.setHasFixedSize(false)
        if (viewModel.eggGroupAdapter.get() == null)
            viewModel.eggGroupAdapter = WeakReference(PokemonEggGroupAdapter())
        viewModel.eggGroupAdapter.get()!!.setUrlProvider(viewModel.urlProvider)
        binding.recyclerViewEggGroupList.adapter = viewModel.eggGroupAdapter.get()
        viewModel.eggGroupAdapter.get()?.setAdapterItemClick(object :
            AdapterItemClickListener<NamedResourceApi> {
            override fun onItemClick(t: NamedResourceApi, pos: Int) {
                if (searchView != null) searchView.clearFocus()
                if (!searchView.query.isNullOrBlank()) {
                    viewModel.filterEggGroups("")
                }
                viewModel.setRecyclerLastPosition(pos)
                val bundle = Bundle()
                bundle.putSerializable(CURRENT_EGG_GROUP, t)
                findNavController().navigate(R.id.action_nav_egg_groups_to_nav_egg_group_detail, bundle)
            }
        })

        binding.recyclerViewEggGroupList.postDelayed({
            binding.recyclerViewEggGroupList.smoothScrollToPosition(viewModel.recyclerLastPos.value.let {
                it ?: 0
            })
        }, 50)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.clear()
        inflater.inflate(R.menu.main, menu)
        val searchItem: MenuItem = menu.findItem(R.id.action_search)

        // getting search view of our item.
        searchView = searchItem.actionView as SearchView

        // below line is to call set on query text listener method.
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(msg: String): Boolean {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                viewModel.filterEggGroups(msg)
                return false
            }
        })
    }

    private fun initViewModel() {
        viewModel.setLimit(viewModel.limit.value.let {
            it ?: resources.getInteger(R.integer.item_limit)
        })
        viewModel.setOffset(viewModel.offset.value.let {
            it ?: resources.getInteger(R.integer.def_offset)
        })
        viewModel.getEggGroups()
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