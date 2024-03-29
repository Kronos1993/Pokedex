package com.kronos.pokedex.ui.pokemon.detail.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kronos.core.extensions.binding.fragmentBinding
import com.kronos.pokedex.R
import com.kronos.pokedex.databinding.FragmentPokemonStatsBinding
import com.kronos.pokedex.domian.model.stat.Stat
import com.kronos.pokedex.ui.pokemon.detail.PokemonDetailViewModel
import com.kronos.pokedex.ui.pokemon.detail.adapter.PokemonStatsAdapter
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
        root
    }

    override fun onResume() {
        super.onResume()
        observeViewModel()
        viewModel.setCurrentTab(3)
    }

    private fun observeViewModel() {
        viewModel.pokemonStats.observe(this.viewLifecycleOwner, ::handlePokemonStats)
        viewModel.pokemonInfo.observe(this.viewLifecycleOwner) { binding.invalidateAll() }
    }


    private fun handlePokemonStats(pokemonStats: List<Stat>) {
        viewModel.pokemonStatAdapter.get()?.submitList(pokemonStats)
        viewModel.pokemonStatAdapter.get()?.notifyItemRangeChanged(0,pokemonStats.size)
        viewModel.pokemonStatAdapter.get()?.setMaxStat(viewModel.statsTotal.get()?:0)
    }

    private fun initViews() {
        initRecyclerPokemonStats()
    }

    private fun initRecyclerPokemonStats() {
        binding.recyclerViewPokemonStats.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewPokemonStats.setHasFixedSize(false)
        if (viewModel.pokemonStatAdapter.get() == null)
            viewModel.pokemonStatAdapter = WeakReference(PokemonStatsAdapter())
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