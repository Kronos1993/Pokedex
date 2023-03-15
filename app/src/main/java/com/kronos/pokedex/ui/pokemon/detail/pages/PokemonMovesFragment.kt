package com.kronos.pokedex.ui.pokemon.detail.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.kronos.core.adapters.AdapterItemClickListener
import com.kronos.core.extensions.binding.fragmentBinding
import com.kronos.pokedex.R
import com.kronos.pokedex.databinding.FragmentPokemonMovesBinding
import com.kronos.pokedex.domian.model.move.MoveList
import com.kronos.pokedex.ui.move.list.PokemonMoveListAdapter
import com.kronos.pokedex.ui.pokemon.detail.CURRENT_TYPE
import com.kronos.pokedex.ui.pokemon.detail.PokemonDetailViewModel
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
        root
    }

    override fun onResume() {
        super.onResume()
        initViews()
        observeViewModel()
    }


    private fun observeViewModel() {
        viewModel.pokemonMoves.observe(this.viewLifecycleOwner, ::handlePokemonMoves)
    }


    private fun handlePokemonMoves(pokemonMoves: List<MoveList>) {
        viewModel.moveByPokemonAdapter.get()?.submitList(pokemonMoves)
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
            viewModel.moveByPokemonAdapter = WeakReference(PokemonMoveListAdapter())
        binding.layoutMove.recyclerViewMoves.adapter = viewModel.moveByPokemonAdapter.get()
        viewModel.moveByPokemonAdapter.get()?.setAdapterItemClick(object :
            AdapterItemClickListener<MoveList> {
            override fun onItemClick(t: MoveList, pos: Int) {
                val bundle = Bundle()
                bundle.putSerializable(CURRENT_TYPE, t)
                Toast.makeText(requireContext(), t.move.name, Toast.LENGTH_SHORT).show()
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

