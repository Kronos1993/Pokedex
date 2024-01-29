package com.kronos.pokedex.ui.pokemon.detail.pages

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.kronos.core.adapters.AdapterItemClickListener
import com.kronos.core.extensions.binding.fragmentBinding
import com.kronos.pokedex.R
import com.kronos.pokedex.databinding.FragmentPokemonMovesBinding
import com.kronos.pokedex.domian.model.move.MoveInfo
import com.kronos.pokedex.domian.model.move.MoveList
import com.kronos.pokedex.ui.move.ShowMoveIn
import com.kronos.pokedex.ui.move.PokemonMoveListAdapter
import com.kronos.pokedex.ui.move.list.CURRENT_MOVE
import com.kronos.pokedex.ui.pokemon.detail.PokemonDetailViewModel
import com.kronos.pokedex.ui.pokemon.detail.adapter.PokemonEvolutionChainAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.lang.ref.WeakReference

@AndroidEntryPoint
class PokemonMovesFragment : Fragment() {

    private val binding by fragmentBinding<FragmentPokemonMovesBinding>(R.layout.fragment_pokemon_moves)

    private val viewModel by activityViewModels<PokemonDetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.run {
        viewModel = this@PokemonMovesFragment.viewModel
        lifecycleOwner = this@PokemonMovesFragment.viewLifecycleOwner
        initViews()
        root
    }

    override fun onResume() {
        super.onResume()
        observeViewModel()
        viewModel.setCurrentTab(3)
    }

    private fun observeViewModel() {
        viewModel.pokemonMovesToShow.observe(this.viewLifecycleOwner, ::handlePokemonMoves)
        viewModel.moveInfo.observe(this.viewLifecycleOwner, ::handleMoveInfo)
    }

    private fun handleMoveInfo(moveInfo: MoveInfo) {
        if (!moveInfo.moveName.isNullOrEmpty()){
            if (findNavController().currentDestination?.id == R.id.nav_pokemon_detail) {
                val bundle = Bundle()
                bundle.putSerializable(CURRENT_MOVE, moveInfo)
                viewModel.postMoveInfo(MoveInfo())
                findNavController().navigate(R.id.action_nav_pokemon_detail_to_nav_move_info_dialog, bundle)
            }
        }
    }

    private fun handlePokemonMoves(pokemonMoves: List<MoveList>) {
        viewModel.moveByPokemonAdapter.get()?.submitList(pokemonMoves)
        viewModel.moveByPokemonAdapter.get()?.notifyItemRangeChanged(0,pokemonMoves.size)
        binding.layoutMove.run {
            moves = pokemonMoves
        }
    }

    private fun initViews() {
        initRecyclerPokemonMoves()
    }

    private fun initRecyclerPokemonMoves() {
        binding.layoutMove.recyclerViewMoves.layoutManager = GridLayoutManager(context,2)
        binding.layoutMove.recyclerViewMoves.setHasFixedSize(false)
        if (viewModel.moveByPokemonAdapter.get() == null)
            viewModel.moveByPokemonAdapter = WeakReference(PokemonMoveListAdapter(ShowMoveIn.POKEMON_DETAIL))
        binding.layoutMove.recyclerViewMoves.adapter = viewModel.moveByPokemonAdapter.get()
        viewModel.moveByPokemonAdapter.get()?.setAdapterItemClick(object :
            AdapterItemClickListener<MoveList> {
            override fun onItemClick(t: MoveList, pos: Int) {
                viewModel.loadMoveInfo(t.move)
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

