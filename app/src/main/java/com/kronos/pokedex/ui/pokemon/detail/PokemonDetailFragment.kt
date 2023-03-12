package com.kronos.pokedex.ui.pokemon.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.kronos.core.adapters.AdapterItemClickListener
import com.kronos.core.extensions.binding.fragmentBinding
import com.kronos.core.util.LoadingDialog
import com.kronos.core.util.show
import com.kronos.pokedex.R
import com.kronos.pokedex.databinding.FragmentPokemonDetailBinding
import com.kronos.pokedex.domian.model.ability.Ability
import com.kronos.pokedex.domian.model.pokemon.PokemonInfo
import com.kronos.pokedex.domian.model.pokemon.PokemonList
import com.kronos.pokedex.domian.model.stat.Stat
import com.kronos.pokedex.domian.model.type.Type
import com.kronos.pokedex.ui.abilities.PokemonAbilityAdapter
import com.kronos.pokedex.ui.pokemon.list.CURRENT_POKEMON
import com.kronos.pokedex.ui.tms.PokemonStatsAdapter
import com.kronos.pokedex.ui.types.PokemonTypeAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.lang.ref.WeakReference
import java.util.*

const val CURRENT_TYPE = "current_type"
const val CURRENT_POKEMON_MOVES = "current_pokemon_moves"

@AndroidEntryPoint
class PokemonDetailFragment : Fragment() {
    private val binding by fragmentBinding<FragmentPokemonDetailBinding>(R.layout.fragment_pokemon_detail)

    private val viewModel by viewModels<PokemonDetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.run {
        viewModel = this@PokemonDetailFragment.viewModel
        lifecycleOwner = this@PokemonDetailFragment.viewLifecycleOwner
        root
    }

    override fun onResume() {
        super.onResume()
        observeViewModel()
        initViewModel()
    }


    private fun observeViewModel() {
        viewModel.pokemonInfo.observe(this.viewLifecycleOwner, ::handlePokemonInfo)
        viewModel.pokemonSpritesUrl.observe(this.viewLifecycleOwner, ::handlePokemonSprites)
        viewModel.loading.observe(this.viewLifecycleOwner, ::handleLoading)
        viewModel.error.observe(this.viewLifecycleOwner, ::handleError)
    }

    private fun handlePokemonInfo(pokemonInfo: PokemonInfo) {
        initViews()
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
        viewModel.pokemonStatAdapter.get()?.submitList(pokemonInfo.stats)
        if (pokemonInfo.abilities.size > 1)
            binding.layoutAbilities.recyclerViewPokemonAbilities.layoutManager = GridLayoutManager(context, 2)

    }

    private fun handlePokemonSprites(sprites: List<String>) {
        viewModel.pokemonSpriteAdapter.get()?.submitList(sprites)
    }

    private fun handleError(hashtable: Hashtable<String, String>) {
        if (hashtable["error"] != null) {
            if (hashtable["error"]!!.isNotEmpty()) {
                show(
                    binding.cardViewPokemonDetailBaseDetail,
                    hashtable["error"].orEmpty(),
                    com.kronos.resources.R.color.snack_bar_white,
                    com.kronos.resources.R.color.snack_bar_error_background
                )
            } else {
                show(
                    binding.cardViewPokemonDetailBaseDetail,
                    hashtable["error"].orEmpty(),
                    com.kronos.resources.R.color.snack_bar_white,
                    com.kronos.resources.R.color.snack_bar_success_background
                )
            }
        }
    }

    private fun handleLoading(b: Boolean) {
        if (b) {
            LoadingDialog.getProgressDialog(
                requireContext(),
                R.string.loading_dialog_text,
                com.kronos.resources.R.color.colorSecondaryVariant
            )!!.show()
        } else {
            LoadingDialog.getProgressDialog(
                requireContext(),
                R.string.loading_dialog_text,
                com.kronos.resources.R.color.colorSecondaryVariant
            )!!.dismiss()
        }
    }

    private fun initViews() {
        initRecyclerPokemonTypes()
        initRecyclerPokemonAbilities()
        initRecyclerPokemonStats()
        initRecyclerPokemonSprites()
        binding.textViewPokemonMoveLink.setOnClickListener{
            val bundle = Bundle()
            bundle.putSerializable(CURRENT_POKEMON_MOVES, viewModel.pokemonInfo.value)
            findNavController().navigate(R.id.action_nav_pokemon_detail_to_nav_moves_by_pokemon_dialog,bundle)
        }
    }

    private fun initRecyclerPokemonSprites() {
        binding.recyclerViewPokemonSprites.layoutManager = GridLayoutManager(context, 2)
        binding.recyclerViewPokemonSprites.setHasFixedSize(false)
        if (viewModel.pokemonSpriteAdapter.get() == null)
            viewModel.pokemonSpriteAdapter = WeakReference(PokemonSpriteAdapter())
        binding.recyclerViewPokemonSprites.adapter = viewModel.pokemonSpriteAdapter.get()
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
        binding.layoutAbilities.recyclerViewPokemonAbilities.layoutManager = LinearLayoutManager(context)
        binding.layoutAbilities.recyclerViewPokemonAbilities.setHasFixedSize(false)
        if (viewModel.pokemonAbilityAdapter.get() == null)
            viewModel.pokemonAbilityAdapter = WeakReference(PokemonAbilityAdapter())
        binding.layoutAbilities.recyclerViewPokemonAbilities.adapter = viewModel.pokemonAbilityAdapter.get()
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

    private fun initRecyclerPokemonStats() {
        binding.recyclerViewPokemonStats.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewPokemonStats.setHasFixedSize(false)
        if (viewModel.pokemonStatAdapter.get() == null)
            viewModel.pokemonStatAdapter = WeakReference(PokemonStatsAdapter())
        binding.recyclerViewPokemonStats.adapter = viewModel.pokemonStatAdapter.get()
        viewModel.pokemonStatAdapter.get()?.setAdapterItemClick(object :
            AdapterItemClickListener<Stat> {
            override fun onItemClick(t: Stat, pos: Int) {
                val bundle = Bundle()
                bundle.putSerializable(CURRENT_TYPE, t)
                Toast.makeText(requireContext(), t.statName, Toast.LENGTH_SHORT).show()
                //findNavController().navigate(R.id.action_nav_pokemon_list_to_nav_pokemon_detail, bundle)
            }
        })
    }

    private fun initViewModel() {
        val bundle = arguments
        if (bundle?.get(CURRENT_POKEMON) != null) {
            viewModel.loadPokemonInfo(bundle.get(CURRENT_POKEMON) as PokemonList)
        } else {
            findNavController().popBackStack()
        }
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