package com.kronos.pokedex.ui.pokemon.list

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
import com.kronos.pokedex.databinding.FragmentPokemonListBinding
import com.kronos.pokedex.domian.model.ResponseListItem
import com.kronos.pokedex.domian.model.pokemon.PokemonDexEntry
import com.kronos.pokedex.domian.model.pokemon.PokemonList
import com.kronos.pokedex.ui.pokedex.CURRENT_POKEDEX
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import java.lang.ref.WeakReference
import java.util.*

const val CURRENT_POKEMON = "current_pokemon"

@AndroidEntryPoint
class PokemonListFragment : Fragment() {

    private val binding by fragmentBinding<FragmentPokemonListBinding>(R.layout.fragment_pokemon_list)

    private val viewModel by viewModels<PokemonListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.run {
        viewModel = this@PokemonListFragment.viewModel
        lifecycleOwner = this@PokemonListFragment.viewLifecycleOwner
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
        viewModel.pokemonList.observe(this.viewLifecycleOwner, ::handlePokemonList)
        viewModel.loading.observe(this.viewLifecycleOwner, ::handleLoading)
        viewModel.error.observe(this.viewLifecycleOwner, ::handleError)
    }


    private fun handleError(hashtable: Hashtable<String, String>) {
        if (hashtable["error"] != null) {
            if (hashtable["error"]!!.isNotEmpty()) {
                show(
                    binding.recyclerViewPokemonList,
                    hashtable["error"].orEmpty(),
                    com.kronos.resources.R.color.snack_bar_white,
                    com.kronos.resources.R.color.snack_bar_error_background
                )
            } else {
                show(
                    binding.recyclerViewPokemonList,
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
        }catch (e:IllegalArgumentException){
            e.printStackTrace()
        }

    }

    private fun handlePokemonList(list: List<PokemonDexEntry>) {
        viewModel.pokemonListAdapter.get()?.submitList(list)
        viewModel.pokemonListAdapter.get()?.notifyDataSetChanged()
    }

    private fun initViews() {
        binding.recyclerViewPokemonList.layoutManager = GridLayoutManager(context,2)
        binding.recyclerViewPokemonList.setHasFixedSize(false)
        if (viewModel.pokemonListAdapter.get() == null)
            viewModel.pokemonListAdapter = WeakReference(PokemonListAdapter())
        viewModel.pokemonListAdapter.get()?.setUrlProvider(viewModel.urlProvider)
        binding.recyclerViewPokemonList.adapter = viewModel.pokemonListAdapter.get()
        viewModel.pokemonListAdapter.get()?.setAdapterItemClick(object :
            AdapterItemClickListener<PokemonDexEntry> {
            override fun onItemClick(t: PokemonDexEntry, pos: Int) {
                val bundle = Bundle()
                bundle.putSerializable(CURRENT_POKEMON, t.pokemon)
                viewModel.setRecyclerLastPosition(pos)
                findNavController().navigate(R.id.action_nav_pokemon_list_to_nav_pokemon_detail, bundle)
            }

        })
        binding.recyclerViewPokemonList.postDelayed({
            binding.recyclerViewPokemonList.smoothScrollToPosition(viewModel.recyclerLastPos.value.let{ it ?: 0 })
        }, 50)
    }

    private fun initViewModel() {
        val bundle = arguments
        if (bundle?.get(CURRENT_POKEDEX) != null) {
            viewModel.getPokemons((bundle.get(CURRENT_POKEDEX) as ResponseListItem).name)
        } else {
            findNavController().popBackStack()
        }
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