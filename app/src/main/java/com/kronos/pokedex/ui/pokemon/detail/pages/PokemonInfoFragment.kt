package com.kronos.pokedex.ui.pokemon.detail.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.kronos.core.adapters.AdapterItemClickListener
import com.kronos.core.extensions.binding.fragmentBinding
import com.kronos.pokedex.R
import com.kronos.pokedex.databinding.FragmentPokemonInfoBinding
import com.kronos.pokedex.domian.model.ability.Ability
import com.kronos.pokedex.domian.model.pokemon.PokemonInfo
import com.kronos.pokedex.domian.model.stat.Stat
import com.kronos.pokedex.domian.model.type.Type
import com.kronos.pokedex.ui.abilities.PokemonAbilityAdapter
import com.kronos.pokedex.ui.pokemon.detail.CURRENT_TYPE
import com.kronos.pokedex.ui.pokemon.detail.PokemonDetailViewModel
import com.kronos.pokedex.ui.pokemon.detail.adapter.PokemonSpriteAdapter
import com.kronos.pokedex.ui.tms.PokemonStatsAdapter
import com.kronos.pokedex.ui.types.PokemonTypeAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.lang.ref.WeakReference

@AndroidEntryPoint
class PokemonInfoFragment : Fragment() {
    private val binding by fragmentBinding<FragmentPokemonInfoBinding>(R.layout.fragment_pokemon_info)

    private val viewModel by activityViewModels<PokemonDetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.run {
        viewModel = this@PokemonInfoFragment.viewModel
        lifecycleOwner = this@PokemonInfoFragment.viewLifecycleOwner
        root
    }

    override fun onResume() {
        super.onResume()
        initViews()
        observeViewModel()
    }


    private fun observeViewModel() {
        viewModel.pokemonInfo.observe(this.viewLifecycleOwner, ::handlePokemonInfo)
        viewModel.pokemonSpritesUrl.observe(this.viewLifecycleOwner, ::handlePokemonSprites)
    }

    private fun handlePokemonInfo(pokemonInfo: PokemonInfo) {
        Glide.with(requireContext()).load(
            pokemonInfo.sprites.let {
                if (!it.frontHome.isNullOrEmpty())
                    it.frontHome
                else
                    it.frontDefault
            }
        ).into(binding.imageViewPokemon)
        viewModel.pokemonTypeAdapter.get()?.submitList(pokemonInfo.types)
        viewModel.pokemonAbilityAdapter.get()?.submitList(pokemonInfo.abilities)
        if (pokemonInfo.abilities.size > 1)
            binding.layoutAbilities.recyclerViewPokemonAbilities.layoutManager =
                GridLayoutManager(context, 2)

    }

    private fun handlePokemonSprites(sprites: List<String>) {
        viewModel.pokemonSpriteAdapter.get()?.submitList(sprites)
    }

    private fun initViews() {
        initRecyclerPokemonTypes()
        initRecyclerPokemonAbilities()
        initRecyclerPokemonSprites()
    }

    private fun initRecyclerPokemonTypes() {
        binding.layoutTypes.recyclerViewPokemonType.layoutManager = GridLayoutManager(context, 2)
        binding.layoutTypes.recyclerViewPokemonType.setHasFixedSize(false)
        if (viewModel.pokemonTypeAdapter.get() == null)
            viewModel.pokemonTypeAdapter = WeakReference(PokemonTypeAdapter())
        binding.layoutTypes.recyclerViewPokemonType.adapter = viewModel.pokemonTypeAdapter.get()
        viewModel.pokemonTypeAdapter.get()?.setAdapterItemClick(object :
            AdapterItemClickListener<Type> {
            override fun onItemClick(t: Type, pos: Int) {
                val bundle = Bundle()
                bundle.putSerializable(CURRENT_TYPE, t)
                Toast.makeText(requireContext(), t.name, Toast.LENGTH_SHORT).show()
                //findNavController().navigate(R.id.action_nav_pokemon_list_to_nav_pokemon_detail, bundle)
            }
        })
    }

    private fun initRecyclerPokemonAbilities() {
        binding.layoutAbilities.recyclerViewPokemonAbilities.layoutManager =
            LinearLayoutManager(context)
        binding.layoutAbilities.recyclerViewPokemonAbilities.setHasFixedSize(false)
        if (viewModel.pokemonAbilityAdapter.get() == null)
            viewModel.pokemonAbilityAdapter = WeakReference(PokemonAbilityAdapter())
        binding.layoutAbilities.recyclerViewPokemonAbilities.adapter =
            viewModel.pokemonAbilityAdapter.get()
        viewModel.pokemonAbilityAdapter.get()?.setAdapterItemClick(object :
            AdapterItemClickListener<Ability> {
            override fun onItemClick(t: Ability, pos: Int) {
                val bundle = Bundle()
                bundle.putSerializable(CURRENT_TYPE, t)
                Toast.makeText(requireContext(), t.ability.name, Toast.LENGTH_SHORT).show()
                //findNavController().navigate(R.id.action_nav_pokemon_list_to_nav_pokemon_detail, bundle)
            }
        })
    }

    private fun initRecyclerPokemonSprites() {
        binding.recyclerViewPokemonSprites.layoutManager = GridLayoutManager(context, 2)
        binding.recyclerViewPokemonSprites.setHasFixedSize(false)
        if (viewModel.pokemonSpriteAdapter.get() == null)
            viewModel.pokemonSpriteAdapter = WeakReference(PokemonSpriteAdapter())
        binding.recyclerViewPokemonSprites.adapter = viewModel.pokemonSpriteAdapter.get()
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