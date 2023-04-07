package com.kronos.pokedex.ui.items.list

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kronos.core.adapters.AdapterItemClickListener
import com.kronos.core.extensions.binding.fragmentBinding
import com.kronos.core.util.LoadingDialog
import com.kronos.core.util.show
import com.kronos.pokedex.R
import com.kronos.pokedex.databinding.FragmentItemsBinding
import com.kronos.pokedex.domian.model.NamedResourceApi
import dagger.hilt.android.AndroidEntryPoint
import java.lang.ref.WeakReference
import java.util.*

const val CURRENT_ITEM = "current_item"

@AndroidEntryPoint
class ItemsFragment : Fragment() {
    private val binding by fragmentBinding<FragmentItemsBinding>(R.layout.fragment_items)

    private val viewModel by viewModels<ItemsViewModel>()

    lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.run {
        viewModel = this@ItemsFragment.viewModel
        lifecycleOwner = this@ItemsFragment.viewLifecycleOwner
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
        viewModel.itemList.observe(this.viewLifecycleOwner, ::handleItems)
        viewModel.loading.observe(this.viewLifecycleOwner, ::handleLoading)
        viewModel.error.observe(this.viewLifecycleOwner, ::handleError)
    }


    private fun handleError(hashtable: Hashtable<String, String>) {
        if (hashtable["error"] != null) {
            if (hashtable["error"]!!.isNotEmpty()) {
                show(
                    binding.recyclerViewItemsList,
                    hashtable["error"].orEmpty(),
                    com.kronos.resources.R.color.snack_bar_white,
                    com.kronos.resources.R.color.snack_bar_error_background
                )
            } else {
                show(
                    binding.recyclerViewItemsList,
                    hashtable["error"].orEmpty(),
                    com.kronos.resources.R.color.snack_bar_white,
                    com.kronos.resources.R.color.snack_bar_success_background
                )
            }
        }
    }

    private fun handleLoading(b: Boolean) {
        try{
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
        }catch (e:Exception){}
    }

    private fun handleItems(list: List<NamedResourceApi>) {
        viewModel.itemListAdapter.get()?.submitList(list)
        viewModel.itemListAdapter.get()?.notifyDataSetChanged()
    }

    private fun initViews() {
        binding.recyclerViewItemsList.layoutManager = GridLayoutManager(context,2)
        binding.recyclerViewItemsList.setHasFixedSize(false)
        if (viewModel.itemListAdapter.get() == null)
            viewModel.itemListAdapter = WeakReference(ItemListAdapter())
        viewModel.itemListAdapter.get()!!.setUrlProvider(viewModel.urlProvider)
        binding.recyclerViewItemsList.adapter = viewModel.itemListAdapter.get()
        viewModel.itemListAdapter.get()?.setAdapterItemClick(object :
            AdapterItemClickListener<NamedResourceApi> {
            override fun onItemClick(t: NamedResourceApi, pos: Int) {
                if (searchView != null) searchView.clearFocus()
                    viewModel.filterItems("")
                val bundle = Bundle()
                bundle.putSerializable(CURRENT_ITEM, t)
                findNavController().navigate(R.id.action_nav_items_to_nav_item_detail, bundle)
            }
        })

        binding.recyclerViewItemsList.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount: Int = (recyclerView.layoutManager as GridLayoutManager).childCount
                val totalItemCount: Int = (recyclerView.layoutManager as GridLayoutManager).itemCount
                val firstVisibleItemPosition: Int = (recyclerView.layoutManager as GridLayoutManager).findFirstVisibleItemPosition()
                if (!viewModel.loading.value!!) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0)
                        viewModel.getMoreItems()
                }
            }
        })
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
                viewModel.filterItems(msg)
                return false
            }
        })
    }

    private fun initViewModel() {
        viewModel.setLimit(viewModel.limit.value.let{ it ?: resources.getInteger(R.integer.item_limit)})
        viewModel.setOffset(viewModel.offset.value.let{ it ?: resources.getInteger(R.integer.def_offset)})
        if (viewModel.itemList.value.isNullOrEmpty())
            viewModel.getBerries()
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