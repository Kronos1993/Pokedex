package com.kronos.pokedex.ui.move.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kronos.core.adapters.AdapterItemClickListener
import com.kronos.core.extensions.binding.fragmentBinding
import com.kronos.pokedex.R
import com.kronos.pokedex.databinding.FragmentDialogMoveInfoBinding
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.move.MoveInfo
import com.kronos.pokedex.domian.model.pokemon.PokemonDexEntry
import com.kronos.pokedex.ui.move.ShowMoveIn
import com.kronos.pokedex.ui.move.list.CURRENT_MOVE
import com.kronos.pokedex.ui.pokemon.detail.PokemonDetailViewModel
import com.kronos.pokedex.ui.pokemon.list.PokemonListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ref.WeakReference

@AndroidEntryPoint
class MoveInfoDialogFragment : BottomSheetDialogFragment() {
    private val binding by fragmentBinding<FragmentDialogMoveInfoBinding>(R.layout.fragment_dialog_move_info)
    private val viewModelPokemonDetail by activityViewModels<PokemonDetailViewModel>()
    private val viewModel by viewModels<MoveInfoViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.run {
        viewModel = this@MoveInfoDialogFragment.viewModel
        lifecycleOwner = this@MoveInfoDialogFragment.viewLifecycleOwner
        setListeners()
        setObservers()
        root
    }

    private fun setListeners() {

    }

    private fun setObservers() {
        viewModel.pokemonList.observe(this.viewLifecycleOwner, ::handlePokemonList)
        viewModel.moveInfo.observe(this.viewLifecycleOwner, ::handleMoveInfo)
    }

    private fun handleMoveInfo(moveInfo: MoveInfo) {
        viewModel.getMoveEffect(moveInfo)
        viewModel.getMoveGameDescription(moveInfo)
        viewModel.loadPokemonList(moveInfo)
        binding.imageViewType.invalidate()
        binding.imageViewPriority.invalidate()
    }

    private fun handlePokemonList(list: MutableList<PokemonDexEntry>?) {
        binding.layoutPokemonList.pokemonList = list
        viewModel.pokemonListAdapter.get()?.submitList(list)
        viewModel.pokemonListAdapter.get()?.notifyDataSetChanged()
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
        if (bundle?.get(CURRENT_MOVE) != null) {
            when {
                bundle.get(CURRENT_MOVE) is MoveInfo -> {
                    viewModel.postMoveInfo((bundle.get(CURRENT_MOVE) as MoveInfo))
                    viewModel.postOrigen(ShowMoveIn.POKEMON_DETAIL)
                }
                bundle.get(CURRENT_MOVE) is NamedResourceApi -> {
                    viewModel.loadMoveInfo((bundle.get(CURRENT_MOVE) as NamedResourceApi))
                    viewModel.postOrigen(ShowMoveIn.MOVE_LIST)
                }
                else -> hideDialog()
            }
        } else {
            hideDialog()
        }
        initView()
    }

    private fun initView() {
        binding.layoutPokemonList.recyclerViewPokemonList.layoutManager =
            GridLayoutManager(context, 2)
        binding.layoutPokemonList.recyclerViewPokemonList.setHasFixedSize(false)
        if (viewModel.pokemonListAdapter.get() == null)
            viewModel.pokemonListAdapter = WeakReference(PokemonListAdapter())
        viewModel.pokemonListAdapter.get()?.setUrlProvider(viewModel.urlProvider)
        binding.layoutPokemonList.recyclerViewPokemonList.adapter =
            viewModel.pokemonListAdapter.get()
        viewModel.pokemonListAdapter.get()?.setAdapterItemClick(object :
            AdapterItemClickListener<PokemonDexEntry> {
            override fun onItemClick(t: PokemonDexEntry, pos: Int) {
                if (viewModel.origen.value == ShowMoveIn.POKEMON_DETAIL)
                    viewModelPokemonDetail.loadPokemonInfo(t.pokemon)
                hideDialog()
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

    override fun onPause() {
        binding.unbind()
        viewModel.postMoveInfo(MoveInfo())
        viewModel.postOrigen(null)
        super.onPause()
    }
}