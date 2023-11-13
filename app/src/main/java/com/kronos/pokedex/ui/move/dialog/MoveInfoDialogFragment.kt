package com.kronos.pokedex.ui.move.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.kronos.core.adapters.AdapterItemClickListener
import com.kronos.core.extensions.binding.fragmentBinding
import com.kronos.pokedex.R
import com.kronos.pokedex.databinding.FragmentMoveInfoBinding
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.move.MoveInfo
import com.kronos.pokedex.domian.model.pokemon.PokemonDexEntry
import com.kronos.pokedex.ui.move.ShowMoveIn
import com.kronos.pokedex.ui.move.list.CURRENT_MOVE
import com.kronos.pokedex.ui.pokemon.list.CURRENT_POKEMON
import com.kronos.pokedex.ui.pokemon.list.PokemonListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.lang.ref.WeakReference

@AndroidEntryPoint
class MoveInfoDialogFragment : Fragment() {
    private val binding by fragmentBinding<FragmentMoveInfoBinding>(R.layout.fragment_move_info)
    private val viewModel by viewModels<MoveInfoViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.run {
        viewModel = this@MoveInfoDialogFragment.viewModel
        lifecycleOwner = this@MoveInfoDialogFragment.viewLifecycleOwner
        setHasOptionsMenu(true)
        root
    }

    override fun onResume() {
        super.onResume()
        setListeners()
        setObservers()
        init()
    }
    
    private fun init(){
        val bundle = arguments
        if (bundle?.get(CURRENT_MOVE) != null) {
            when {
                bundle.get(CURRENT_MOVE) is MoveInfo -> {
                    viewModel.postMoveInfo((bundle.get(CURRENT_MOVE) as MoveInfo))
                    viewModel.postOrigen(ShowMoveIn.POKEMON_DETAIL)
                    initView()
                }
                bundle.get(CURRENT_MOVE) is NamedResourceApi -> {
                    viewModel.loadMoveInfo((bundle.get(CURRENT_MOVE) as NamedResourceApi))
                    viewModel.postOrigen(ShowMoveIn.MOVE_LIST)
                    initView()
                }
                else -> goBack()
            }
        } else {
            goBack()
        }

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
        viewModel.getMoveName(moveInfo)
        viewModel.loadPokemonList(moveInfo)
        binding.imageViewType.invalidate()
        binding.imageViewPriority.invalidate()
    }

    private fun handlePokemonList(list: MutableList<PokemonDexEntry>?) {
        runBlocking{
            binding.layoutPokemonList.run {
                pokemonList = list
            }
            viewModel.pokemonListAdapter.get()?.submitList(list)
            viewModel.pokemonListAdapter.get()?.notifyItemRangeChanged(0,list!!.size)
        }
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
                val bundle = Bundle()
                bundle.putSerializable(CURRENT_POKEMON, t.pokemon)
                findNavController().navigate(
                    R.id.action_nav_move_info_dialog_to_nav_pokemon_detail,
                    bundle
                )
            }
        })
    }

    private fun goBack() {
        lifecycleScope.launch {
            withContext(Dispatchers.Main) {
                findNavController().popBackStack()
            }
        }
    }

    override fun onDestroy() {
        binding.unbind()
        viewModel.postMoveInfo(MoveInfo())
        viewModel.postOrigen(null)
        super.onDestroy()
    }
}