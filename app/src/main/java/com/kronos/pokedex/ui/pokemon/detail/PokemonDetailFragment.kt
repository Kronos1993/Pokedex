package com.kronos.pokedex.ui.pokemon.detail

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.kronos.core.extensions.binding.fragmentBinding
import com.kronos.core.util.LoadingDialog
import com.kronos.core.util.show
import com.kronos.pokedex.R
import com.kronos.pokedex.databinding.FragmentPokemonDetailBinding
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.ability.AbilityInfo
import com.kronos.pokedex.domian.model.pokemon.PokemonInfo
import com.kronos.pokedex.ui.pokemon.detail.adapter.PokemonInfoPageAdapter
import com.kronos.pokedex.ui.pokemon.detail.pages.PokemonEvolutionFragment
import com.kronos.pokedex.ui.pokemon.detail.pages.PokemonInfoFragment
import com.kronos.pokedex.ui.pokemon.detail.pages.PokemonMovesFragment
import com.kronos.pokedex.ui.pokemon.detail.pages.PokemonStatsFragment
import com.kronos.pokedex.ui.pokemon.list.CURRENT_POKEMON
import dagger.hilt.android.AndroidEntryPoint
import java.lang.ref.WeakReference
import java.util.*

const val CURRENT_TYPE = "current_type"

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
        initViews()
        initViewModel()
    }

    private fun observeViewModel() {
        viewModel.pokemonInfo.observe(this.viewLifecycleOwner, ::handlePokemonInfo)
        viewModel.loading.observe(this.viewLifecycleOwner, ::handleLoading)
        viewModel.error.observe(this.viewLifecycleOwner, ::handleError)
    }

    private fun handlePokemonInfo(pokemonInfo: PokemonInfo) {
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
        }catch (e:Exception){}

    }

    private fun initViews() {
        var pageInfo = Triple("Info",PokemonInfoFragment(),ContextCompat.getDrawable(requireContext(), R.drawable.ic_pokemon_info))
        var pageEvo = Triple("Evolution",PokemonEvolutionFragment(),ContextCompat.getDrawable(requireContext(), R.drawable.ic_pokemon_evolution))
        var pageStats = Triple("Stats",PokemonStatsFragment(),ContextCompat.getDrawable(requireContext(), R.drawable.ic_pokemon_stats))
        var pageMoves = Triple("Moves",PokemonMovesFragment(),ContextCompat.getDrawable(requireContext(), R.drawable.ic_pokemon_move))

        viewModel.pokemonInfoPageAdapter = WeakReference(
            PokemonInfoPageAdapter(
                childFragmentManager,
                lifecycle,
                mutableListOf(
                    pageInfo,pageEvo,pageStats,pageMoves
                )
            )
        )
        binding.viewPagerPokemonInfo.adapter = viewModel.pokemonInfoPageAdapter.get()
        TabLayoutMediator(binding.tabPokemonData, binding.viewPagerPokemonInfo) { tab, index ->
            tab.text = viewModel.pokemonInfoPageAdapter.get()!!.getPageTitle(index)
            tab.icon = viewModel.pokemonInfoPageAdapter.get()!!.getPageIcon(index)
        }.attach()
    }

    private fun initViewModel() {
        val bundle = arguments
        if (bundle?.get(CURRENT_POKEMON) != null) {
            viewModel.loadPokemonInfo(bundle.get(CURRENT_POKEMON) as NamedResourceApi)
        } else {
            findNavController().popBackStack()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.pokemon_detail, menu)
        super.onCreateOptionsMenu(menu, inflater);
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {
                Toast.makeText(requireContext(),"Settings",Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        binding.unbind()
        super.onDestroyView()
    }

    override fun onPause() {
        viewModel.pokemonInfoPageAdapter = WeakReference(null)
        viewModel.pokemonAbilityAdapter = WeakReference(null)
        viewModel.pokemonSpriteAdapter = WeakReference(null)
        viewModel.pokemonStatAdapter = WeakReference(null)
        viewModel.pokemonTypeAdapter = WeakReference(null)
        viewModel.postPokemonInfo(PokemonInfo())
        viewModel.postPokemonMoves(listOf())
        viewModel.postPokemonStats(listOf())
        viewModel.postAbilityInfo(AbilityInfo())
        viewModel.postPokemonSprites(listOf())
        viewModel.postPokemonOtherForms(listOf())
        binding.unbind()
        super.onPause()
    }


}