package com.kronos.pokedex.ui.egg_group.detail

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kronos.core.adapters.AdapterItemClickListener
import com.kronos.core.extensions.binding.fragmentBinding
import com.kronos.pokedex.R
import com.kronos.pokedex.databinding.FragmentDialogEggGrupInfoBinding
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.egg_group.EggGroupInfo
import com.kronos.pokedex.domian.model.pokemon.PokemonDexEntry
import com.kronos.pokedex.ui.egg_group.list.CURRENT_EGG_GROUP
import com.kronos.pokedex.ui.pokemon.list.CURRENT_POKEMON
import com.kronos.pokedex.ui.pokemon.list.PokemonListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ref.WeakReference

@AndroidEntryPoint
class EggGroupDetailFragment : BottomSheetDialogFragment() {
    private val binding by fragmentBinding<FragmentDialogEggGrupInfoBinding>(R.layout.fragment_dialog_egg_grup_info)

    private val viewModel by activityViewModels<EggGroupDetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.run {
        viewModel = this@EggGroupDetailFragment.viewModel
        lifecycleOwner = this@EggGroupDetailFragment.viewLifecycleOwner
        setHasOptionsMenu(true)
        root
    }

    override fun onResume() {
        super.onResume()
        observeViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpDialog()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(true)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    private fun setUpDialog() {
        this.isCancelable = true
        val bundle = arguments
        if (bundle?.get(CURRENT_EGG_GROUP) != null) {
            if(bundle.get(CURRENT_EGG_GROUP) is NamedResourceApi)
                viewModel.loadEggGroupInfo((bundle.get(CURRENT_EGG_GROUP) as NamedResourceApi))
            else
                viewModel.postEggGroupInfo((bundle.get(CURRENT_EGG_GROUP) as EggGroupInfo))
        } else {
            hideDialog()
        }
    }

    private fun observeViewModel() {
        viewModel.eggGroupInfo.observe(this.viewLifecycleOwner, ::handleEggGroup)
    }

    private fun handleEggGroup(eggGroup: EggGroupInfo) {
        handlePokemon(eggGroup.pokemonSpecies)
        viewModel.getEggGroupName(eggGroup)
    }

    private fun handlePokemon(pokemonList:List<NamedResourceApi>) {
        if (viewModel.pokemonListAdapter.get() == null)
            viewModel.pokemonListAdapter = WeakReference(PokemonListAdapter())
        var pokemonDexEntry:List<PokemonDexEntry> = pokemonList.map {
            PokemonDexEntry(viewModel.urlProvider.extractIdFromUrl(it.url),it)
        }
        viewModel.pokemonListAdapter.get()!!.submitList(pokemonDexEntry)
        viewModel.pokemonListAdapter.get()!!.setUrlProvider(viewModel.urlProvider)

        binding.layoutPokemonList.pokemonList = pokemonDexEntry
        binding.layoutPokemonList.recyclerViewPokemonList.layoutManager =
            GridLayoutManager(context, 2)
        binding.layoutPokemonList.recyclerViewPokemonList.setHasFixedSize(false)
        binding.layoutPokemonList.recyclerViewPokemonList.adapter =
            viewModel.pokemonListAdapter.get()
        viewModel.pokemonListAdapter.get()?.setAdapterItemClick(object :
            AdapterItemClickListener<PokemonDexEntry> {
            override fun onItemClick(t: PokemonDexEntry, pos: Int) {
                val bundle = Bundle()
                bundle.putSerializable(CURRENT_POKEMON, t.pokemon)
                findNavController().navigate(R.id.action_nav_egg_group_detail_to_nav_pokemon_detail,bundle)
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

    override fun onDestroyView() {
        binding.unbind()
        super.onDestroyView()
    }

    override fun onPause() {
        viewModel.postEggGroupInfo(EggGroupInfo())
        viewModel.pokemonListAdapter = WeakReference(null)
        super.onPause()
    }

}