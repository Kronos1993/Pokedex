package com.kronos.pokedex.ui.move.list

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
import com.kronos.pokedex.databinding.FragmentMoveListBinding
import com.kronos.pokedex.domian.model.move.MoveList
import com.kronos.pokedex.ui.move.PokemonMoveListAdapter
import com.kronos.pokedex.ui.move.ShowMoveIn
import dagger.hilt.android.AndroidEntryPoint
import java.lang.ref.WeakReference
import java.util.*

const val CURRENT_MOVE = "current_move"

@AndroidEntryPoint
class MoveListFragment : Fragment() {

    private val binding by fragmentBinding<FragmentMoveListBinding>(R.layout.fragment_move_list)

    private val viewModel by viewModels<MoveListViewModel>()

    lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.run {
        viewModel = this@MoveListFragment.viewModel
        lifecycleOwner = this@MoveListFragment.viewLifecycleOwner
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
        viewModel.moveList.observe(this.viewLifecycleOwner, ::handleMoveList)
        viewModel.loading.observe(this.viewLifecycleOwner, ::handleLoading)
        viewModel.error.observe(this.viewLifecycleOwner, ::handleError)
    }


    private fun handleError(hashtable: Hashtable<String, String>) {
        if (hashtable["error"] != null) {
            if (hashtable["error"]!!.isNotEmpty()) {
                show(
                    binding.recyclerViewMoves,
                    hashtable["error"].orEmpty(),
                    com.kronos.resources.R.color.snack_bar_white,
                    com.kronos.resources.R.color.snack_bar_error_background
                )
            } else {
                show(
                    binding.recyclerViewMoves,
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

    private fun handleMoveList(list: List<MoveList>) {
        viewModel.moveAdapter.get()?.submitList(list)
        viewModel.moveAdapter.get()?.notifyDataSetChanged()
    }

    private fun initViews() {
        binding.recyclerViewMoves.layoutManager =
            GridLayoutManager(context, 2)
        binding.recyclerViewMoves.setHasFixedSize(false)
        if (viewModel.moveAdapter.get() == null)
            viewModel.moveAdapter = WeakReference(PokemonMoveListAdapter(ShowMoveIn.MOVE_LIST))
        binding.recyclerViewMoves.adapter =
            viewModel.moveAdapter.get()
        viewModel.moveAdapter.get()?.setAdapterItemClick(object :
            AdapterItemClickListener<MoveList> {
            override fun onItemClick(t: MoveList, pos: Int) {
                if (searchView != null)
                    searchView.clearFocus()
                if (!searchView.query.isNullOrBlank()) {
                    viewModel.filterMove("")
                }
                viewModel.setRecyclerLastPosition(pos)
                if (!t.move.name.isNullOrEmpty()) {
                    if (findNavController().currentDestination?.id == R.id.nav_move_list) {
                        val bundle = Bundle()
                        bundle.putSerializable(CURRENT_MOVE, t.move)
                        findNavController().navigate(
                            R.id.action_nav_move_list_to_nav_move_info_dialog,
                            bundle
                        )
                    }

                }
            }

        })
        binding.recyclerViewMoves.postDelayed({
            binding.recyclerViewMoves.smoothScrollToPosition(viewModel.recyclerLastPos.value.let {
                it ?: 0
            })
        }, 50)

        binding.recyclerViewMoves.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount: Int = (recyclerView.layoutManager as GridLayoutManager).childCount
                val totalItemCount: Int = (recyclerView.layoutManager as GridLayoutManager).itemCount
                val firstVisibleItemPosition: Int = (recyclerView.layoutManager as GridLayoutManager).findFirstVisibleItemPosition()
                if (!viewModel.loading.value!!) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0)
                        viewModel.getMoreMoves()
                }
            }
        })

        binding.btnRefresh.setOnClickListener {
            if (viewModel.moveOriginalList.value.isNullOrEmpty())
                viewModel.getMoves()
        }
    }

    private fun initViewModel() {
        viewModel.setLimit(viewModel.limit.value.let {
            it ?: resources.getInteger(R.integer.move_limit)
        })
        viewModel.setOffset(viewModel.offset.value.let {
            it ?: resources.getInteger(R.integer.def_offset)
        })
        if (viewModel.moveOriginalList.value.isNullOrEmpty())
            viewModel.getMoves()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        // Inflate the menu; this adds items to the action bar if it is present.
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
                viewModel.filterMove(msg)
                return false
            }
        })
    }

    override fun onPause() {
        binding.unbind()
        super.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.unbind()
    }
}