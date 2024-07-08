package com.kronos.pokedex.ui.pokedex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.pedant.SweetAlert.SweetAlertDialog
import com.kronos.core.adapters.AdapterItemClickListener
import com.kronos.core.extensions.binding.fragmentBinding
import com.kronos.core.util.getProgressDialog
import com.kronos.core.util.show
import com.kronos.pokedex.R
import com.kronos.pokedex.databinding.FragmentPokedexBinding
import com.kronos.pokedex.domian.model.NamedResourceApi
import dagger.hilt.android.AndroidEntryPoint
import java.lang.ref.WeakReference
import java.util.Hashtable

const val CURRENT_POKEDEX = "current_pokedex"

@AndroidEntryPoint
class PokedexFragment : Fragment() {
    private val binding by fragmentBinding<FragmentPokedexBinding>(R.layout.fragment_pokedex)

    private val viewModel by viewModels<PokedexViewModel>()

    lateinit var searchView: SearchView
    private var progressDialog: SweetAlertDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.run {
        viewModel = this@PokedexFragment.viewModel
        lifecycleOwner = this@PokedexFragment.viewLifecycleOwner
        setHasOptionsMenu(true)
        progressDialog = getProgressDialog(
            requireContext(),
            com.kronos.resources.R.string.loading_dialog_text,
            com.kronos.resources.R.color.colorPrimary
        )
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
        try{
            if (b) {
                progressDialog?.show()
            } else {
                progressDialog?.dismiss()
            }
        }catch (e:Exception){}
    }

    private fun handlePokedexList(list: List<NamedResourceApi>) {
        viewModel.pokedexListAdapter.get()?.submitList(list)
        viewModel.pokedexListAdapter.get()?.notifyItemRangeChanged(0,list.size)
    }

    private fun initViews() {
        binding.recyclerViewPokedexList.layoutManager = GridLayoutManager(context,2)
        binding.recyclerViewPokedexList.setHasFixedSize(false)
        if (viewModel.pokedexListAdapter.get() == null)
            viewModel.pokedexListAdapter = WeakReference(PokedexListAdapter())
        binding.recyclerViewPokedexList.adapter = viewModel.pokedexListAdapter.get()
        viewModel.pokedexListAdapter.get()?.setAdapterItemClick(object :
            AdapterItemClickListener<NamedResourceApi> {
            override fun onItemClick(t: NamedResourceApi, pos: Int) {
                if (searchView != null) searchView.clearFocus()
                if (!searchView.query.isNullOrBlank()) {
                    viewModel.filterPokedex("")
                }
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

        binding.btnRefresh.setOnClickListener {
            if (viewModel.pokedexList.value.isNullOrEmpty())
                viewModel.getPokedex()
        }
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
                viewModel.filterPokedex(msg)
                return false
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