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
import com.kronos.pokedex.domian.model.pokemon.PokemonInfo
import com.kronos.pokedex.domian.model.stat.Stat
import com.kronos.pokedex.ui.pokemon.detail.CURRENT_TYPE
import com.kronos.pokedex.ui.pokemon.detail.PokemonDetailViewModel
import com.kronos.pokedex.ui.tms.PokemonStatsAdapter
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
        root
    }

    override fun onResume() {
        super.onResume()
        initViews()
        observeViewModel()
    }


    private fun observeViewModel() {
        viewModel.pokemonStats.observe(this.viewLifecycleOwner, ::handlePokemonStats)
    }


    private fun handlePokemonStats(pokemonStats: List<Stat>) {
        viewModel.pokemonStatAdapter.get()?.submitList(pokemonStats)
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
        viewModel.pokemonStatAdapter.get()?.setAdapterItemClick(object :
            AdapterItemClickListener<Stat> {
            override fun onItemClick(t: Stat, pos: Int) {
                val bundle = Bundle()
                bundle.putSerializable(CURRENT_TYPE, t)
                Toast.makeText(requireContext(), t.statName, Toast.LENGTH_SHORT).show()
                //findNavController().navigate(R.id.action_nav_pokemon_list_to_nav_pokemon_detail, bundle)
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