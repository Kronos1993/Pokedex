package com.kronos.pokedex.ui.types.list

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
import com.kronos.pokedex.databinding.FragmentTypeListBinding
import com.kronos.pokedex.domian.model.NamedResourceApi
import dagger.hilt.android.AndroidEntryPoint
import java.lang.ref.WeakReference
import java.util.*

const val CURRENT_TYPE = "current_type"

@AndroidEntryPoint
class TypeListFragment : Fragment() {

    private val binding by fragmentBinding<FragmentTypeListBinding>(R.layout.fragment_type_list)

    private val viewModel by viewModels<TypeListViewModel>()

    lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.run {
        viewModel = this@TypeListFragment.viewModel
        lifecycleOwner = this@TypeListFragment.viewLifecycleOwner
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
        viewModel.typeList.observe(this.viewLifecycleOwner, ::handleTypeList)
        viewModel.loading.observe(this.viewLifecycleOwner, ::handleLoading)
        viewModel.error.observe(this.viewLifecycleOwner, ::handleError)
    }


    private fun handleError(hashtable: Hashtable<String, String>) {
        if (hashtable["error"] != null) {
            if (hashtable["error"]!!.isNotEmpty()) {
                show(
                    binding.recyclerViewPokemonType,
                    hashtable["error"].orEmpty(),
                    com.kronos.resources.R.color.snack_bar_white,
                    com.kronos.resources.R.color.snack_bar_error_background
                )
            } else {
                show(
                    binding.recyclerViewPokemonType,
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
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }

    }

    private fun handleTypeList(list: List<NamedResourceApi>) {
        viewModel.typeListAdapter.get()?.submitList(list)
        viewModel.typeListAdapter.get()?.notifyDataSetChanged()
    }

    private fun initViews() {
        binding.recyclerViewPokemonType.layoutManager = GridLayoutManager(context, 2)
        binding.recyclerViewPokemonType.setHasFixedSize(false)
        if (viewModel.typeListAdapter.get() == null)
            viewModel.typeListAdapter = WeakReference(TypeAdapter())
        viewModel.typeListAdapter.get()?.setUrlProvider(viewModel.urlProvider)
        binding.recyclerViewPokemonType.adapter = viewModel.typeListAdapter.get()
        viewModel.typeListAdapter.get()?.setAdapterItemClick(object :
            AdapterItemClickListener<NamedResourceApi> {
            override fun onItemClick(t: NamedResourceApi, pos: Int) {
                if (searchView != null) searchView.clearFocus()
                if (!searchView.query.isNullOrBlank()) {
                    viewModel.filterTypes("")
                }
                val bundle = Bundle()
                bundle.putSerializable(CURRENT_TYPE, t)
                viewModel.setRecyclerLastPosition(pos)
                findNavController().navigate(R.id.action_nav_types_to_nav_type_detail, bundle)
            }

        })
        binding.recyclerViewPokemonType.postDelayed({
            binding.recyclerViewPokemonType.smoothScrollToPosition(viewModel.recyclerLastPos.value.let {
                it ?: 0
            })
        }, 50)

        binding.recyclerViewPokemonType.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount: Int =
                    (recyclerView.layoutManager as GridLayoutManager).childCount
                val totalItemCount: Int =
                    (recyclerView.layoutManager as GridLayoutManager).itemCount
                val firstVisibleItemPosition: Int =
                    (recyclerView.layoutManager as GridLayoutManager).findFirstVisibleItemPosition()
                if (!viewModel.loading.value!!) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0)
                        viewModel.getMoreTypes()
                }
            }
        })

        binding.btnRefresh.setOnClickListener {
            if (viewModel.typeOriginalList.value.isNullOrEmpty())
                viewModel.getTypes()
        }
    }

    private fun initViewModel() {
        viewModel.setLimit(viewModel.limit.value.let {
            it ?: resources.getInteger(R.integer.move_limit)
        })
        viewModel.setOffset(viewModel.offset.value.let {
            it ?: resources.getInteger(R.integer.def_offset)
        })
        if (viewModel.typeOriginalList.value.isNullOrEmpty())
            viewModel.getTypes()
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
                viewModel.filterTypes(msg)
                return false
            }
        })
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