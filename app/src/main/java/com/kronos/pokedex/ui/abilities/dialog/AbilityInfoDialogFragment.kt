package com.kronos.pokedex.ui.abilities.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kronos.core.adapters.AdapterItemClickListener
import com.kronos.core.extensions.binding.fragmentBinding
import com.kronos.pokedex.R
import com.kronos.pokedex.databinding.FragmentDialogAbilityInfoBinding
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.ability.AbilityInfo
import com.kronos.pokedex.domian.model.pokemon.PokemonDexEntry
import com.kronos.pokedex.ui.abilities.detail.AbilityInfoViewModel
import com.kronos.pokedex.ui.abilities.list.CURRENT_ABILITY
import com.kronos.pokedex.ui.pokemon.list.CURRENT_POKEMON
import com.kronos.pokedex.ui.pokemon.list.PokemonListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ref.WeakReference

@AndroidEntryPoint
class AbilityInfoDialogFragment : BottomSheetDialogFragment()  {
    private val binding by fragmentBinding<FragmentDialogAbilityInfoBinding>(R.layout.fragment_dialog_ability_info)

    private val viewModel by viewModels<AbilityInfoViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.run {
        viewModel = this@AbilityInfoDialogFragment.viewModel
        lifecycleOwner = this@AbilityInfoDialogFragment.viewLifecycleOwner
        setListeners()
        setObservers()
        root
    }

    private fun setListeners() {
        binding.textViewAbilityViewMore.setOnClickListener {
            //go to ability detail fragment passing named resource api
        }
    }

    private fun setObservers() {
        viewModel.pokemonList.observe(this.viewLifecycleOwner, ::handlePokemonList)
        viewModel.abilityInfo.observe(this.viewLifecycleOwner, ::handleAbilityInfo)
    }

    private fun handleAbilityInfo(abilityInfo: AbilityInfo) {
        viewModel.getAbilityEffect(abilityInfo)
        viewModel.getAbilityGameDescription(abilityInfo)
        viewModel.loadPokemonList(abilityInfo)
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
        if (bundle?.get(CURRENT_ABILITY) != null) {
            when {
                bundle.get(CURRENT_ABILITY) is AbilityInfo -> viewModel.postAbilityInfo((bundle.get(CURRENT_ABILITY) as AbilityInfo))
                bundle.get(CURRENT_ABILITY) is NamedResourceApi -> viewModel.loadAbilityInfo((bundle.get(CURRENT_ABILITY) as NamedResourceApi))
                else -> hideDialog()
            }
        } else {
            hideDialog()
        }
        initView()
    }

    private fun initView() {
        binding.layoutPokemonList.recyclerViewPokemonList.layoutManager = GridLayoutManager(context,2)
        binding.layoutPokemonList.recyclerViewPokemonList.setHasFixedSize(false)
        if (viewModel.pokemonListAdapter.get() == null)
            viewModel.pokemonListAdapter = WeakReference(PokemonListAdapter())
        viewModel.pokemonListAdapter.get()?.setUrlProvider(viewModel.urlProvider)
        binding.layoutPokemonList.recyclerViewPokemonList.adapter = viewModel.pokemonListAdapter.get()
        viewModel.pokemonListAdapter.get()?.setAdapterItemClick(object :
            AdapterItemClickListener<PokemonDexEntry> {
            override fun onItemClick(t: PokemonDexEntry, pos: Int) {
                val bundle = Bundle()
                bundle.putSerializable(CURRENT_POKEMON, t.pokemon)
                findNavController().navigate(R.id.action_nav_ability_info_dialog_to_nav_pokemon_detail, bundle)
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