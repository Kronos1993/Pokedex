package com.kronos.pokedex.ui.pokemon.detail.pages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kronos.core.adapters.AdapterItemClickListener
import com.kronos.core.extensions.binding.fragmentBinding
import com.kronos.pokedex.R
import com.kronos.pokedex.databinding.FragmentPokemonStatsBinding
import com.kronos.pokedex.domian.model.stat.Stat
import com.kronos.pokedex.ui.pokemon.detail.CURRENT_TYPE
import com.kronos.pokedex.ui.pokemon.detail.PokemonDetailViewModel
import com.kronos.pokedex.ui.stats.PokemonStatsAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.lang.ref.WeakReference

@AndroidEntryPoint
class PokemonStatsFragment : Fragment() {
    private val binding by fragmentBinding<FragmentPokemonStatsBinding>(R.layout.fragment_pokemon_stats)

    private val viewModel by activityViewModels<PokemonDetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.run {
        viewModel = this@PokemonStatsFragment.viewModel
        lifecycleOwner = this@PokemonStatsFragment.viewLifecycleOwner
        initViews()
        observeViewModel()
        root
    }

    private fun observeViewModel() {
        viewModel.pokemonStats.observe(this.viewLifecycleOwner, ::handlePokemonStats)
        viewModel.pokemonInfo.observe(this.viewLifecycleOwner) { binding.invalidateAll() }
    }


    private fun handlePokemonStats(pokemonStats: List<Stat>) {
        viewModel.pokemonStatAdapter.get()?.submitList(pokemonStats)
        viewModel.pokemonStatAdapter.get()?.notifyDataSetChanged()
    }

    private fun initViews() {
        initRecyclerPokemonStats()
    }

    private fun initRecyclerPokemonStats() {
        binding.recyclerViewPokemonStats.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewPokemonStats.setHasFixedSize(false)
        if (viewModel.pokemonStatAdapter.get() == null)
            viewModel.pokemonStatAdapter = WeakReference(PokemonStatsAdapter())
        viewModel.pokemonStatAdapter.get()?.setMaxStat(viewModel.statsTotal.get()?:0)
        binding.recyclerViewPokemonStats.adapter = viewModel.pokemonStatAdapter.get()
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