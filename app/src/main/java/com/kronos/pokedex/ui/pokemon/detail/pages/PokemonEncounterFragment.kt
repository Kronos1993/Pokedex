package com.kronos.pokedex.ui.pokemon.detail.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kronos.core.adapters.AdapterItemClickListener
import com.kronos.core.extensions.binding.fragmentBinding
import com.kronos.pokedex.R
import com.kronos.pokedex.databinding.FragmentPokemonEncounterBinding
import com.kronos.pokedex.domian.model.pokemon.Encounter
import com.kronos.pokedex.ui.pokemon.detail.PokemonDetailViewModel
import com.kronos.pokedex.ui.pokemon.detail.adapter.PokemonEncounterItemAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.lang.ref.WeakReference

@AndroidEntryPoint
class PokemonEncounterFragment : Fragment() {
    private val binding by fragmentBinding<FragmentPokemonEncounterBinding>(R.layout.fragment_pokemon_encounter)

    private val viewModel by activityViewModels<PokemonDetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.run {
        viewModel = this@PokemonEncounterFragment.viewModel
        lifecycleOwner = this@PokemonEncounterFragment.viewLifecycleOwner
        initViews()
        root
    }

    override fun onResume() {
        super.onResume()
        observeViewModel()
        viewModel.setCurrentTab(1)
        viewModel.getPokemonEncounters()
    }

    private fun observeViewModel() {
        viewModel.pokemonEncounterList.observe(this.viewLifecycleOwner, ::handlePokemonEncounters)
    }

    private fun handlePokemonEncounters(list: List<Encounter>) {
        viewModel.pokemonEncounterItemAdapter.get()?.submitList(list)
        viewModel.pokemonEncounterItemAdapter.get()?.notifyItemRangeChanged(0,list.size)
        binding.invalidateAll()
    }

    private fun initViews() {
        initRecyclerPokemonEncounter()
    }

    private fun initRecyclerPokemonEncounter() {
        binding.recyclerViewPokemonEncounters.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewPokemonEncounters.setHasFixedSize(false)
        if (viewModel.pokemonEncounterItemAdapter.get() == null)
            viewModel.pokemonEncounterItemAdapter = WeakReference(PokemonEncounterItemAdapter())
        viewModel.pokemonEncounterItemAdapter.get()?.setUrlProvider(viewModel.urlProvider)
        binding.recyclerViewPokemonEncounters.adapter = viewModel.pokemonEncounterItemAdapter.get()
        viewModel.pokemonEncounterItemAdapter.get()?.setAdapterItemClick(object :
            AdapterItemClickListener<Encounter> {
            override fun onItemClick(t: Encounter, pos: Int) {
                //viewModel.loadPokemonInfo(t)
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