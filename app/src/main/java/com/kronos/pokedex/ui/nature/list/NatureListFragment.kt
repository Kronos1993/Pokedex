package com.kronos.pokedex.ui.nature.list

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
import com.kronos.pokedex.databinding.FragmentNatureListBinding
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.nature.NatureDetail
import dagger.hilt.android.AndroidEntryPoint
import java.lang.ref.WeakReference
import java.util.Hashtable

const val CURRENT_NATURE = "current_nature"

@AndroidEntryPoint
class NatureListFragment : Fragment() {

    private val binding by fragmentBinding<FragmentNatureListBinding>(R.layout.fragment_nature_list)

    private val viewModel by viewModels<NatureListViewModel>()

    lateinit var searchView: SearchView
    private var progressDialog: SweetAlertDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.run {
        viewModel = this@NatureListFragment.viewModel
        lifecycleOwner = this@NatureListFragment.viewLifecycleOwner
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
        viewModel.natureList.observe(this.viewLifecycleOwner, ::handleNatureList)
        viewModel.natureInfoSelected.observe(this.viewLifecycleOwner, ::handleNatureDetail)
        viewModel.loading.observe(this.viewLifecycleOwner, ::handleLoading)
        viewModel.error.observe(this.viewLifecycleOwner, ::handleError)
    }

    private fun handleNatureDetail(natureDetail: NatureDetail) {
        if(!natureDetail.name.isNullOrEmpty()){
            val bundle = Bundle()
            bundle.putSerializable(CURRENT_NATURE, natureDetail)
            findNavController().navigate(R.id.action_nav_nature_list_to_nav_detail_info_dialog, bundle)
        }
    }

        private fun handleError(hashtable: Hashtable<String, String>) {
        if (hashtable["error"] != null) {
            if (hashtable["error"]!!.isNotEmpty()) {
                show(
                    binding.layoutNatureList.recyclerViewNatures,
                    hashtable["error"].orEmpty(),
                    com.kronos.resources.R.color.snack_bar_white,
                    com.kronos.resources.R.color.snack_bar_error_background
                )
            } else {
                show(
                    binding.layoutNatureList.recyclerViewNatures,
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

    private fun handleNatureList(list: List<NamedResourceApi>) {
        viewModel.natureListAdapter.get()?.submitList(list)
        binding.layoutNatureList.natures = list
        viewModel.natureListAdapter.get()?.notifyDataSetChanged()
    }

    private fun initViews() {
        binding.layoutNatureList.recyclerViewNatures.layoutManager = GridLayoutManager(context,2)
        binding.layoutNatureList.recyclerViewNatures.setHasFixedSize(false)
        if (viewModel.natureListAdapter.get() == null)
            viewModel.natureListAdapter = WeakReference(NatureListAdapter())
        binding.layoutNatureList.recyclerViewNatures.adapter = viewModel.natureListAdapter.get()
        viewModel.natureListAdapter.get()?.setAdapterItemClick(object :
            AdapterItemClickListener<NamedResourceApi> {
            override fun onItemClick(t: NamedResourceApi, pos: Int) {
                if (searchView != null) searchView.clearFocus()
                if (!searchView.query.isNullOrBlank()) {
                    viewModel.filterNature("")
                }
                viewModel.loadNatureInfo(t)
            }
        })

        binding.layoutNatureList.recyclerViewNatures.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount: Int = (recyclerView.layoutManager as GridLayoutManager).childCount
                val totalItemCount: Int = (recyclerView.layoutManager as GridLayoutManager).itemCount
                val firstVisibleItemPosition: Int = (recyclerView.layoutManager as GridLayoutManager).findFirstVisibleItemPosition()
                if (!viewModel.loading.value!!) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0)
                        viewModel.getMoreNature()
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
                viewModel.filterNature(msg)
                return false
            }
        })
    }

    private fun initViewModel() {
        viewModel.setLimit(viewModel.limit.value.let{ it ?: resources.getInteger(R.integer.def_limit)})
        viewModel.setOffset(viewModel.offset.value.let{ it ?: resources.getInteger(R.integer.def_offset)})
        if (viewModel.natureList.value.isNullOrEmpty())
            viewModel.getNatures()
    }

    override fun onDestroyView() {
        binding.unbind()
        super.onDestroyView()
    }

    override fun onPause() {
        viewModel.postNatureInfo(NatureDetail())
        binding.unbind()
        super.onPause()
    }

}