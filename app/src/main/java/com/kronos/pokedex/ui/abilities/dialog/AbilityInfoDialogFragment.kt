package com.kronos.pokedex.ui.abilities.dialog

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
import com.kronos.pokedex.databinding.FragmentAbilityInfoBinding
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.ability.AbilityInfo
import com.kronos.pokedex.domian.model.pokemon.PokemonDexEntry
import com.kronos.pokedex.ui.abilities.ShowAbilityIn
import com.kronos.pokedex.ui.abilities.list.CURRENT_ABILITY
import com.kronos.pokedex.ui.pokemon.list.CURRENT_POKEMON
import com.kronos.pokedex.ui.pokemon.list.PokemonListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ref.WeakReference

@AndroidEntryPoint
class AbilityInfoDialogFragment : Fragment() {
    private val binding by fragmentBinding<FragmentAbilityInfoBinding>(R.layout.fragment_ability_info)
    private val viewModel by viewModels<AbilityInfoViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.run {
        viewModel = this@AbilityInfoDialogFragment.viewModel
        lifecycleOwner = this@AbilityInfoDialogFragment.viewLifecycleOwner
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
        if (bundle?.get(CURRENT_ABILITY) != null) {
            when {
                bundle.get(CURRENT_ABILITY) is AbilityInfo -> {
                    viewModel.postAbilityInfo((bundle.get(CURRENT_ABILITY) as AbilityInfo))
                    viewModel.postOrigen(ShowAbilityIn.POKEMON_DETAIL)
                    initView()
                }
                bundle.get(CURRENT_ABILITY) is NamedResourceApi -> {
                    viewModel.loadAbilityInfo((bundle.get(CURRENT_ABILITY) as NamedResourceApi))
                    viewModel.postOrigen(ShowAbilityIn.ABILITY_LIST)
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
        viewModel.abilityInfo.observe(this.viewLifecycleOwner, ::handleAbilityInfo)
    }

    private fun handleAbilityInfo(abilityInfo: AbilityInfo) {
        viewModel.getAbilityEffect(abilityInfo)
        viewModel.getAbilityGameDescription(abilityInfo)
        viewModel.loadPokemonList(abilityInfo)
        viewModel.getAbilityName(abilityInfo)
    }

    private fun handlePokemonList(list: MutableList<PokemonDexEntry>?) {
        binding.layoutPokemonList.pokemonList = list
        viewModel.pokemonListAdapter.get()?.submitList(list)
        viewModel.pokemonListAdapter.get()?.notifyDataSetChanged()
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
                    R.id.action_nav_ability_info_dialog_to_nav_pokemon_detail,
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
        viewModel.postAbilityInfo(AbilityInfo())
        viewModel.postOrigen(null)
        super.onDestroy()
    }

}