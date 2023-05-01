package com.kronos.pokedex.ui.pokemon.detail.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.kronos.core.adapters.AdapterItemClickListener
import com.kronos.core.extensions.binding.fragmentBinding
import com.kronos.pokedex.R
import com.kronos.pokedex.databinding.FragmentPokemonInfoBinding
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.ability.Ability
import com.kronos.pokedex.domian.model.ability.AbilityInfo
import com.kronos.pokedex.domian.model.pokemon.PokemonInfo
import com.kronos.pokedex.domian.model.type.Type
import com.kronos.pokedex.ui.abilities.PokemonAbilityAdapter
import com.kronos.pokedex.ui.abilities.list.CURRENT_ABILITY
import com.kronos.pokedex.ui.pokemon.detail.CURRENT_TYPE
import com.kronos.pokedex.ui.pokemon.detail.PokemonDetailViewModel
import com.kronos.pokedex.ui.pokemon.detail.adapter.PokemonEggGroupAdapter
import com.kronos.pokedex.ui.pokemon.detail.adapter.PokemonSpriteAdapter
import com.kronos.pokedex.ui.show_image.CURRENT_IMAGE_URL
import com.kronos.pokedex.ui.pokemon.detail.adapter.PokemonTypeAdapter
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
        initViews()
        observeViewModel()
        root
    }

    private fun observeViewModel() {
        viewModel.pokemonInfo.observe(this.viewLifecycleOwner, ::handlePokemonInfo)
        viewModel.pokemonSpritesUrl.observe(this.viewLifecycleOwner, ::handlePokemonSprites)
        viewModel.pokemonOtherFormsUrl.observe(this.viewLifecycleOwner, ::handlePokemonOtherForms)
        viewModel.abilityInfo.observe(this.viewLifecycleOwner, ::handleAbilityInfo)
    }

    private fun handleAbilityInfo(abilityInfo: AbilityInfo) {
        if (!abilityInfo.name.isNullOrEmpty()) {
            if (findNavController().currentDestination?.id == R.id.nav_pokemon_detail) {
                val bundle = Bundle()
                bundle.putSerializable(CURRENT_ABILITY, abilityInfo)
                findNavController().navigate(
                    R.id.action_nav_pokemon_detail_to_nav_ability_info_dialog_fragment,
                    bundle
                )
            }
        }
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

        binding.imageViewPokemon.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable(CURRENT_IMAGE_URL, pokemonInfo.sprites.let {
                if (!it.frontHome.isNullOrEmpty())
                    it.frontHome
                else
                    it.frontDefault
            })
            findNavController().navigate(R.id.action_global_nav_show_image, bundle)
        }

        viewModel.pokemonTypeAdapter.get()?.submitList(pokemonInfo.types)
        viewModel.pokemonTypeAdapter.get()?.notifyDataSetChanged()
        if (pokemonInfo.types.size > 1)
            binding.layoutTypes.recyclerViewPokemonType.layoutManager =
                GridLayoutManager(context, 2)

        if (pokemonInfo.specie.eggGroup.size > 1)
            binding.recyclerViewEggGroup.layoutManager = GridLayoutManager(context,2)

        viewModel.pokemonEggGroupAdapter.get()?.submitList(pokemonInfo.specie.eggGroup)
        viewModel.pokemonEggGroupAdapter.get()?.notifyDataSetChanged()

        viewModel.pokemonAbilityAdapter.get()?.submitList(pokemonInfo.abilities)
        viewModel.pokemonAbilityAdapter.get()?.notifyDataSetChanged()

        if (pokemonInfo.abilities.size > 1)
            binding.layoutAbilities.recyclerViewPokemonAbilities.layoutManager =
                GridLayoutManager(context, 2)

        binding.invalidateAll()

    }

    private fun handlePokemonSprites(sprites: List<Pair<String, String>>) {
        viewModel.pokemonSpriteAdapter.get()?.submitList(sprites)
        viewModel.pokemonSpriteAdapter.get()?.notifyDataSetChanged()
    }

    private fun handlePokemonOtherForms(sprites: List<Pair<String, String>>) {
        viewModel.pokemonOtherFormsAdapter.get()?.submitList(sprites)
        viewModel.pokemonOtherFormsAdapter.get()?.notifyDataSetChanged()
    }

    private fun initViews() {
        initRecyclerPokemonTypes()
        initRecyclerPokemonEggGroup()
        initRecyclerPokemonAbilities()
        initRecyclerPokemonSprites()
        initRecyclerPokemonOtherForms()
    }

    private fun initRecyclerPokemonTypes() {
        binding.layoutTypes.recyclerViewPokemonType.layoutManager = LinearLayoutManager(context)
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

    private fun initRecyclerPokemonEggGroup() {
        binding.recyclerViewEggGroup.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewEggGroup.setHasFixedSize(false)
        if (viewModel.pokemonEggGroupAdapter.get() == null)
            viewModel.pokemonEggGroupAdapter = WeakReference(PokemonEggGroupAdapter())
        binding.recyclerViewEggGroup.adapter = viewModel.pokemonEggGroupAdapter.get()
        viewModel.pokemonEggGroupAdapter.get()?.setAdapterItemClick(object :
            AdapterItemClickListener<NamedResourceApi> {
            override fun onItemClick(t: NamedResourceApi, pos: Int) {
                /*val bundle = Bundle()
                bundle.putSerializable(CURRENT_TYPE, t)
                Toast.makeText(requireContext(), t.name, Toast.LENGTH_SHORT).show()*/
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
                viewModel.loadAbilityInfo(t.ability)
            }
        })
    }

    private fun initRecyclerPokemonSprites() {
        binding.recyclerViewPokemonSprites.layoutManager = GridLayoutManager(context, 2)
        binding.recyclerViewPokemonSprites.setHasFixedSize(false)
        if (viewModel.pokemonSpriteAdapter.get() == null)
            viewModel.pokemonSpriteAdapter = WeakReference(PokemonSpriteAdapter())
        viewModel.pokemonSpriteAdapter.get()?.setUrlProvider(viewModel.urlProvider)
        binding.recyclerViewPokemonSprites.adapter = viewModel.pokemonSpriteAdapter.get()
        viewModel.pokemonSpriteAdapter.get()?.setAdapterItemClick(object :
            AdapterItemClickListener<Pair<String, String>> {
            override fun onItemClick(t: Pair<String, String>, pos: Int) {
                val bundle = Bundle()
                bundle.putSerializable(CURRENT_IMAGE_URL, t.first)
                findNavController().navigate(R.id.action_global_nav_show_image, bundle)
            }
        })
    }

    private fun initRecyclerPokemonOtherForms() {
        binding.recyclerViewPokemonOtherForms.layoutManager = GridLayoutManager(context, 2)
        binding.recyclerViewPokemonOtherForms.setHasFixedSize(false)
        if (viewModel.pokemonOtherFormsAdapter.get() == null)
            viewModel.pokemonOtherFormsAdapter = WeakReference(PokemonSpriteAdapter())
        viewModel.pokemonOtherFormsAdapter.get()?.setUrlProvider(viewModel.urlProvider)
        binding.recyclerViewPokemonOtherForms.adapter = viewModel.pokemonOtherFormsAdapter.get()
        viewModel.pokemonOtherFormsAdapter.get()?.setAdapterItemClick(object :
            AdapterItemClickListener<Pair<String, String>> {
            override fun onItemClick(t: Pair<String, String>, pos: Int) {
                viewModel.loadPokemonInfo(NamedResourceApi(t.second, t.first))
            }
        })
    }

    override fun onDestroyView() {
        binding.unbind()
        super.onDestroyView()
    }

    override fun onPause() {
        binding.unbind()
        viewModel.postAbilityInfo(AbilityInfo())
        super.onPause()
    }
}