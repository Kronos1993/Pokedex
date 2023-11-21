package com.kronos.pokedex.ui.pokemon.detail

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.databinding.ObservableField
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.kronos.core.extensions.asLiveData
import com.kronos.core.extensions.binding.fragmentBinding
import com.kronos.core.util.LoadingDialog
import com.kronos.core.util.show
import com.kronos.pokedex.R
import com.kronos.pokedex.databinding.FragmentPokemonDetailBinding
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.ability.AbilityInfo
import com.kronos.pokedex.domian.model.evolution_chain.ChainLink
import com.kronos.pokedex.domian.model.evolution_chain.EvolutionChain
import com.kronos.pokedex.domian.model.move.MoveInfo
import com.kronos.pokedex.domian.model.move.MoveList
import com.kronos.pokedex.domian.model.pokemon.PokemonInfo
import com.kronos.pokedex.domian.model.specie.SpecieInfo
import com.kronos.pokedex.domian.model.stat.Stat
import com.kronos.pokedex.ui.abilities.PokemonAbilityAdapter
import com.kronos.pokedex.ui.move.PokemonMoveListAdapter
import com.kronos.pokedex.ui.move.ShowMoveIn
import com.kronos.pokedex.ui.pokemon.detail.adapter.PokemonEggGroupAdapter
import com.kronos.pokedex.ui.pokemon.detail.adapter.PokemonEvolutionChainAdapter
import com.kronos.pokedex.ui.pokemon.detail.adapter.PokemonInfoPageAdapter
import com.kronos.pokedex.ui.pokemon.detail.adapter.PokemonSpriteAdapter
import com.kronos.pokedex.ui.pokemon.detail.adapter.PokemonTypeAdapter
import com.kronos.pokedex.ui.pokemon.detail.domain.GenderPossibility
import com.kronos.pokedex.ui.pokemon.detail.pages.PokemonEvolutionFragment
import com.kronos.pokedex.ui.pokemon.detail.pages.PokemonGamesFragment
import com.kronos.pokedex.ui.pokemon.detail.pages.PokemonInfoFragment
import com.kronos.pokedex.ui.pokemon.detail.pages.PokemonMovesFragment
import com.kronos.pokedex.ui.pokemon.detail.pages.PokemonStatsFragment
import com.kronos.pokedex.ui.pokemon.list.CURRENT_POKEMON
import com.kronos.pokedex.ui.stats.PokemonStatsAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.lang.ref.WeakReference
import java.util.*

@AndroidEntryPoint
class PokemonDetailFragment : Fragment() {
    private val binding by fragmentBinding<FragmentPokemonDetailBinding>(R.layout.fragment_pokemon_detail)

    private val viewModel by activityViewModels<PokemonDetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.run {
        viewModel = this@PokemonDetailFragment.viewModel
        lifecycleOwner = this@PokemonDetailFragment.viewLifecycleOwner
        setHasOptionsMenu(true)
        root
    }

    override fun onResume() {
        super.onResume()
        observeViewModel()
        initViewModel()
        initViews()
    }

    private fun observeViewModel() {
        viewModel.pokemonInfo.observe(this.viewLifecycleOwner, ::handlePokemonInfo)
        viewModel.loading.observe(this.viewLifecycleOwner, ::handleLoading)
        viewModel.error.observe(this.viewLifecycleOwner, ::handleError)
    }

    private fun handlePokemonInfo(pokemonInfo: PokemonInfo?) {
        binding.viewPagerPokemonInfo.setCurrentItem(0,true)
    }

    private fun handleError(hashtable: Hashtable<String, String>) {
        if (hashtable["error"] != null) {
            if (hashtable["error"]!!.isNotEmpty()) {
                show(
                    binding.tabPokemonData,
                    hashtable["error"].orEmpty(),
                    com.kronos.resources.R.color.snack_bar_white,
                    com.kronos.resources.R.color.snack_bar_error_background
                )
            } else {
                show(
                    binding.tabPokemonData,
                    hashtable["error"].orEmpty(),
                    com.kronos.resources.R.color.snack_bar_white,
                    com.kronos.resources.R.color.snack_bar_success_background
                )
            }
        }
    }

    private fun handleLoading(b: Boolean) {
        try{
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
        }catch (_:Exception){}

    }

    private fun initViews() {
        val pageInfo = Triple("Info",PokemonInfoFragment(),ContextCompat.getDrawable(requireContext(), R.drawable.ic_pokemon_info))
        val pageEvo = Triple("Evolution",PokemonEvolutionFragment(),ContextCompat.getDrawable(requireContext(), R.drawable.ic_pokemon_evolution))
        val pageStats = Triple("Stats",PokemonStatsFragment(),ContextCompat.getDrawable(requireContext(), R.drawable.ic_pokemon_stats))
        val pageMoves = Triple("Moves",PokemonMovesFragment(),ContextCompat.getDrawable(requireContext(), R.drawable.ic_pokemon_tm))
        val pageGames = Triple("Games",PokemonGamesFragment(),ContextCompat.getDrawable(requireContext(), R.drawable.ic_game_boy))

        viewModel.pokemonInfoPageAdapter = WeakReference(
            PokemonInfoPageAdapter(
                childFragmentManager,
                lifecycle,
                mutableListOf(
                    pageInfo,pageEvo,pageStats,pageMoves,pageGames
                )
            )
        )
        binding.viewPagerPokemonInfo.adapter = viewModel.pokemonInfoPageAdapter.get()
        binding.viewPagerPokemonInfo.isSaveEnabled = false
        TabLayoutMediator(binding.tabPokemonData, binding.viewPagerPokemonInfo) { tab, index ->
            tab.text = viewModel.pokemonInfoPageAdapter.get()!!.getPageTitle(index)
            tab.icon = viewModel.pokemonInfoPageAdapter.get()!!.getPageIcon(index)
        }.attach()
    }

    private fun initViewModel() {
        if(viewModel.pokemonInfo.value == null){
            val bundle = arguments
            if (bundle?.get(CURRENT_POKEMON) != null) {
                viewModel.loadPokemonInfo(bundle.get(CURRENT_POKEMON) as NamedResourceApi)
            } else {
                findNavController().popBackStack()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.screen_detail, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroyView() {
        binding.unbind()
        super.onDestroyView()
    }

    override fun onDestroy() {
        binding.unbind()
        super.onDestroy()
    }

    override fun onPause() {
        binding.unbind()
        viewModel.destroyViewModel()
        super.onPause()
    }
}