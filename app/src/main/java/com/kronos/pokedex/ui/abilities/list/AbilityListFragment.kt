package com.kronos.pokedex.ui.abilities.list

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
import com.kronos.pokedex.databinding.FragmentAbilityListBinding
import com.kronos.pokedex.domian.model.ability.Ability
import com.kronos.pokedex.ui.abilities.PokemonAbilityAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.lang.ref.WeakReference
import java.util.*

const val CURRENT_ABILITY = "current_ability"

@AndroidEntryPoint
class AbilityListFragment : Fragment() {

    private val binding by fragmentBinding<FragmentAbilityListBinding>(R.layout.fragment_ability_list)

    private val viewModel by viewModels<AbilityListViewModel>()

    lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.run {
        viewModel = this@AbilityListFragment.viewModel
        lifecycleOwner = this@AbilityListFragment.viewLifecycleOwner
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
        viewModel.abilityList.observe(this.viewLifecycleOwner, ::handleAbilitiesList)
        viewModel.loading.observe(this.viewLifecycleOwner, ::handleLoading)
        viewModel.error.observe(this.viewLifecycleOwner, ::handleError)
    }


    private fun handleError(hashtable: Hashtable<String, String>) {
        if (hashtable["error"] != null) {
            if (hashtable["error"]!!.isNotEmpty()) {
                show(
                    binding.layoutAbilityList.recyclerViewPokemonAbilities,
                    hashtable["error"].orEmpty(),
                    com.kronos.resources.R.color.snack_bar_white,
                    com.kronos.resources.R.color.snack_bar_error_background
                )
            } else {
                show(
                    binding.layoutAbilityList.recyclerViewPokemonAbilities,
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

    private fun handleAbilitiesList(list: List<Ability>) {
        viewModel.abilityAdapter.get()?.submitList(list)
        viewModel.abilityAdapter.get()?.notifyDataSetChanged()
    }

    private fun initViews() {
        binding.layoutAbilityList.recyclerViewPokemonAbilities.layoutManager =
            GridLayoutManager(context, 2)
        binding.layoutAbilityList.recyclerViewPokemonAbilities.setHasFixedSize(false)
        if (viewModel.abilityAdapter.get() == null)
            viewModel.abilityAdapter = WeakReference(PokemonAbilityAdapter())
        binding.layoutAbilityList.recyclerViewPokemonAbilities.adapter =
            viewModel.abilityAdapter.get()
        viewModel.abilityAdapter.get()?.setAdapterItemClick(object :
            AdapterItemClickListener<Ability> {
            override fun onItemClick(t: Ability, pos: Int) {
                if (searchView != null) searchView.clearFocus()
                viewModel.filterAbility("")
                if (!t.ability.name.isNullOrEmpty()) {
                    if (findNavController().currentDestination?.id == R.id.nav_abilities) {
                        val bundle = Bundle()
                        bundle.putSerializable(CURRENT_ABILITY, t.ability)
                        findNavController().navigate(
                            R.id.action_nav_ability_list_to_nav_ability_info_dialog,
                            bundle
                        )
                    }

                }
            }

        })
        binding.layoutAbilityList.recyclerViewPokemonAbilities.postDelayed({
            binding.layoutAbilityList.recyclerViewPokemonAbilities.smoothScrollToPosition(viewModel.recyclerLastPos.value.let {
                it ?: 0
            })
        }, 50)

        binding.layoutAbilityList.recyclerViewPokemonAbilities.addOnScrollListener(object :
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
                        viewModel.getMoreAbilities()
                }
            }
        })
    }

    private fun initViewModel() {
        viewModel.setLimit(viewModel.limit.value.let {
            it ?: resources.getInteger(R.integer.ability_limit)
        })
        viewModel.setOffset(viewModel.offset.value.let {
            it ?: resources.getInteger(R.integer.def_offset)
        })
        if (viewModel.abilityOriginalList.value.isNullOrEmpty())
            viewModel.getAbilities()
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
                viewModel.filterAbility(msg)
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