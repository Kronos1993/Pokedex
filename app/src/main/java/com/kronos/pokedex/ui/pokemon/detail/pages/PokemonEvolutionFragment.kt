package com.kronos.pokedex.ui.pokemon.detail.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kronos.core.adapters.AdapterItemClickListener
import com.kronos.core.extensions.binding.fragmentBinding
import com.kronos.pokedex.R
import com.kronos.pokedex.databinding.FragmentPokemonEvolutionBinding
import com.kronos.pokedex.domian.model.pokemon.PokemonInfo
import com.kronos.pokedex.domian.model.stat.Stat
import com.kronos.pokedex.ui.pokemon.detail.CURRENT_TYPE
import com.kronos.pokedex.ui.pokemon.detail.PokemonDetailViewModel
import com.kronos.pokedex.ui.tms.PokemonStatsAdapter
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
        root
    }

    override fun onResume() {
        super.onResume()
        initViews()
        observeViewModel()
    }

    private fun observeViewModel() {
    }

    private fun initViews() {
    }

    private fun initRecyclerPokemonStats() {

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