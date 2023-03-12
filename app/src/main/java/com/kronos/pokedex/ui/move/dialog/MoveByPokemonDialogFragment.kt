package com.kronos.pokedex.ui.move.dialog

import android.app.Dialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kronos.core.adapters.AdapterItemClickListener
import com.kronos.core.extensions.binding.fragmentBinding
import com.kronos.pokedex.R
import com.kronos.pokedex.databinding.FragmentDialogMoveByPokemonBinding
import com.kronos.pokedex.domian.model.move.MoveList
import com.kronos.pokedex.domian.model.pokemon.PokemonInfo
import com.kronos.pokedex.domian.model.pokemon.PokemonList
import com.kronos.pokedex.domian.model.type.Type
import com.kronos.pokedex.ui.move.list.PokemonMoveListAdapter
import com.kronos.pokedex.ui.pokemon.detail.CURRENT_POKEMON_MOVES
import com.kronos.pokedex.ui.pokemon.detail.CURRENT_TYPE
import com.kronos.pokedex.ui.pokemon.list.CURRENT_POKEMON
import com.kronos.pokedex.ui.types.PokemonTypeAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ref.WeakReference

@AndroidEntryPoint
class MoveByPokemonDialogFragment : BottomSheetDialogFragment() {

    private val binding by fragmentBinding<FragmentDialogMoveByPokemonBinding>(R.layout.fragment_dialog_move_by_pokemon)

    private val viewModel by viewModels<MoveByPokemonDialogViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.run {
        viewModel = this@MoveByPokemonDialogFragment.viewModel
        lifecycleOwner = this@MoveByPokemonDialogFragment.viewLifecycleOwner
        setListeners()
        setObservers()
        root
    }

    private fun setListeners() {

    }

    private fun setObservers() {
        viewModel.pokemonMoveList.observe(this.viewLifecycleOwner, ::handlePokemonMoves)
    }

    private fun handlePokemonMoves(list: MutableList<MoveList>?) {
        viewModel.moveByPokemonAdapter.get()?.submitList(list)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpDialog()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    private fun setUpDialog() {
        this.isCancelable = true
        val bundle = arguments
        if (bundle?.get(CURRENT_POKEMON) != null) {
            viewModel.postPokemonMove((bundle.get(CURRENT_POKEMON) as PokemonInfo).moves)
        } else {
            findNavController().popBackStack()
        }
        initRecyclerPokemonMoves()
    }

    private fun initRecyclerPokemonMoves() {
        binding.layoutMove.recyclerViewMoves.layoutManager = GridLayoutManager(context, 2)
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

    private fun hideDialog() {
        lifecycleScope.launch {
            withContext(Dispatchers.Main) {
                findNavController().navigateUp()
            }
        }
    }

    override fun onDestroy() {
        binding.unbind()
        super.onDestroy()
    }
}