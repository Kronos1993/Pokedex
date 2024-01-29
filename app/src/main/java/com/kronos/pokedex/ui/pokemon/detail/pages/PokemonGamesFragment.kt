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
import com.kronos.pokedex.databinding.FragmentPokemonGamesBinding
import com.kronos.pokedex.domian.model.game.Game
import com.kronos.pokedex.ui.pokemon.detail.PokemonDetailViewModel
import com.kronos.pokedex.ui.pokemon.detail.adapter.PokemonGameAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.lang.ref.WeakReference

@AndroidEntryPoint
class PokemonGamesFragment : Fragment() {
    private val binding by fragmentBinding<FragmentPokemonGamesBinding>(R.layout.fragment_pokemon_games)

    private val viewModel by activityViewModels<PokemonDetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.run {
        viewModel = this@PokemonGamesFragment.viewModel
        lifecycleOwner = this@PokemonGamesFragment.viewLifecycleOwner
        initViews()
        root
    }

    override fun onResume() {
        super.onResume()
        observeViewModel()
        viewModel.setCurrentTab(4)
    }

    private fun observeViewModel() {
        viewModel.pokemonGames.observe(this.viewLifecycleOwner, ::handlePokemonGames)
    }

    private fun handlePokemonGames(list: List<Game>) {
        viewModel.pokemonGamesAdapter.get()?.submitList(list)
        viewModel.pokemonGamesAdapter.get()?.notifyItemRangeChanged(0,list.size)
        binding.invalidateAll()
    }

    private fun initViews() {
        initRecyclerPokemonEvolution()
    }

    private fun initRecyclerPokemonEvolution() {
        binding.recyclerViewPokemonGame.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewPokemonGame.setHasFixedSize(false)
        if (viewModel.pokemonGamesAdapter.get() == null)
            viewModel.pokemonGamesAdapter = WeakReference(PokemonGameAdapter())
        viewModel.pokemonGamesAdapter.get()?.setUrlProvider(viewModel.urlProvider)
        binding.recyclerViewPokemonGame.adapter = viewModel.pokemonGamesAdapter.get()
        viewModel.pokemonGamesAdapter.get()?.setAdapterItemClick(object :
            AdapterItemClickListener<Game> {
            override fun onItemClick(t: Game, pos: Int) {
                //viewModel.loadPokemonInfo(t.species)
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