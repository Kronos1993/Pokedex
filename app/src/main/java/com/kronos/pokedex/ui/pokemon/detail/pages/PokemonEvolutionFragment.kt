package com.kronos.pokedex.ui.pokemon.detail.pages

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kronos.core.adapters.AdapterItemClickListener
import com.kronos.core.extensions.binding.fragmentBinding
import com.kronos.pokedex.R
import com.kronos.pokedex.databinding.FragmentPokemonEvolutionBinding
import com.kronos.pokedex.domian.model.evolution_chain.ChainLink
import com.kronos.pokedex.ui.pokemon.detail.PokemonDetailViewModel
import com.kronos.pokedex.ui.pokemon.detail.adapter.PokemonEvolutionChainAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.lang.ref.WeakReference

@AndroidEntryPoint
class PokemonEvolutionFragment : Fragment() {
    private val binding by fragmentBinding<FragmentPokemonEvolutionBinding>(R.layout.fragment_pokemon_evolution)

    private val viewModel by activityViewModels<PokemonDetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.run {
        viewModel = this@PokemonEvolutionFragment.viewModel
        lifecycleOwner = this@PokemonEvolutionFragment.viewLifecycleOwner
        initViews()
        root
    }

    override fun onResume() {
        super.onResume()
        observeViewModel()
        viewModel.setCurrentTab(1)
    }

    private fun observeViewModel() {
        viewModel.pokemonEvolutionList.observe(this.viewLifecycleOwner, ::handlePokemonEvolution)
    }

    private fun handlePokemonEvolution(list: List<ChainLink>) {
        viewModel.evolutionPokemonAdapter.get()?.submitList(list)
        viewModel.evolutionPokemonAdapter.get()?.notifyItemRangeChanged(0,list.size)
        binding.invalidateAll()
    }

    private fun initViews() {
        viewModel.getPokemonEvolution()
        initRecyclerPokemonEvolution()
    }

    private fun initRecyclerPokemonEvolution() {
        binding.recyclerViewPokemonEvolution.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewPokemonEvolution.setHasFixedSize(false)
        if (viewModel.evolutionPokemonAdapter.get() == null)
            viewModel.evolutionPokemonAdapter = WeakReference(PokemonEvolutionChainAdapter())
        viewModel.evolutionPokemonAdapter.get()?.setUrlProvider(viewModel.urlProvider)
        binding.recyclerViewPokemonEvolution.adapter = viewModel.evolutionPokemonAdapter.get()
        viewModel.evolutionPokemonAdapter.get()?.setAdapterItemClick(object :
            AdapterItemClickListener<ChainLink> {
            override fun onItemClick(t: ChainLink, pos: Int) {
                viewModel.loadPokemonInfo(t.species)
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