package com.kronos.pokedex.ui.items.detail

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.kronos.core.adapters.AdapterItemClickListener
import com.kronos.core.extensions.binding.fragmentBinding
import com.kronos.core.util.LoadingDialog
import com.kronos.core.util.show
import com.kronos.pokedex.R
import com.kronos.pokedex.databinding.FragmentItemInfoBinding
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.item.ItemInfo
import com.kronos.pokedex.domian.model.pokemon.PokemonDexEntry
import com.kronos.pokedex.ui.items.list.CURRENT_ITEM
import com.kronos.pokedex.ui.pokemon.list.CURRENT_POKEMON
import com.kronos.pokedex.ui.pokemon.list.PokemonListAdapter
import com.kronos.pokedex.ui.show_image.CURRENT_IMAGE_URL
import dagger.hilt.android.AndroidEntryPoint
import java.lang.ref.WeakReference
import java.util.*

@AndroidEntryPoint
class ItemDetailFragment : Fragment() {
    private val binding by fragmentBinding<FragmentItemInfoBinding>(R.layout.fragment_item_info)

    private val viewModel by activityViewModels<ItemDetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.run {
        viewModel = this@ItemDetailFragment.viewModel
        lifecycleOwner = this@ItemDetailFragment.viewLifecycleOwner
        setHasOptionsMenu(true)
        root
    }

    override fun onResume() {
        super.onResume()
        observeViewModel()
        initViewModel()
    }

    private fun observeViewModel() {
        viewModel.itemInfo.observe(this.viewLifecycleOwner, ::handleItemInfo)
        viewModel.loading.observe(this.viewLifecycleOwner, ::handleLoading)
        viewModel.error.observe(this.viewLifecycleOwner, ::handleError)
    }

    private fun handleItemInfo(itemInfo: ItemInfo) {
        handlePokemon(itemInfo.heldByPokemon)
        viewModel.getItemName(itemInfo)
        if (!itemInfo.sprites.defaultImg.isNullOrEmpty()){
            binding.imageViewItemSprite.setOnClickListener {
                val bundle = Bundle()
                bundle.putSerializable(CURRENT_IMAGE_URL, itemInfo.sprites.defaultImg)
                findNavController().navigate(R.id.action_global_nav_show_image, bundle)
            }
        }

    }

    private fun handleError(hashtable: Hashtable<String, String>) {
        if (hashtable["error"] != null) {
            if (hashtable["error"]!!.isNotEmpty()) {
                show(
                    binding.cardViewBaseItemDetail,
                    hashtable["error"].orEmpty(),
                    com.kronos.resources.R.color.snack_bar_white,
                    com.kronos.resources.R.color.snack_bar_error_background
                )
            } else {
                show(
                    binding.cardViewBaseItemDetail,
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

    private fun handlePokemon(pokemonList:List<NamedResourceApi>) {
        if (viewModel.pokemonListAdapter.get() == null)
            viewModel.pokemonListAdapter = WeakReference(PokemonListAdapter())
        var pokemonDexEntry:List<PokemonDexEntry> = pokemonList.map {
            PokemonDexEntry(viewModel.urlProvider.extractIdFromUrl(it.url),it)
        }
        viewModel.pokemonListAdapter.get()!!.submitList(pokemonDexEntry)
        viewModel.pokemonListAdapter.get()!!.setUrlProvider(viewModel.urlProvider)

        binding.layoutPokemon.pokemonList = pokemonDexEntry
        binding.layoutPokemon.recyclerViewPokemonList.layoutManager =
            GridLayoutManager(context, 2)
        binding.layoutPokemon.recyclerViewPokemonList.setHasFixedSize(false)
        binding.layoutPokemon.recyclerViewPokemonList.adapter =
            viewModel.pokemonListAdapter.get()
        viewModel.pokemonListAdapter.get()?.setAdapterItemClick(object :
            AdapterItemClickListener<PokemonDexEntry> {
            override fun onItemClick(t: PokemonDexEntry, pos: Int) {
                val bundle = Bundle()
                bundle.putSerializable(CURRENT_POKEMON, t.pokemon)
                findNavController().navigate(R.id.action_nav_item_detail_to_nav_pokemon_detail,bundle)
            }
        })
    }

    private fun initViewModel() {
        val bundle = arguments
        if (bundle?.get(CURRENT_ITEM) != null) {
            viewModel.loadItemInfo(bundle.get(CURRENT_ITEM) as NamedResourceApi)
        } else {
            findNavController().popBackStack()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.screen_detail, menu)
        super.onCreateOptionsMenu(menu, inflater);
    }

    override fun onDestroyView() {
        binding.unbind()
        viewModel.postItemInfo(ItemInfo())
        viewModel.pokemonListAdapter = WeakReference(null)
        viewModel.itemDescription.set(null)
        viewModel.itemEffect.set(null)
        viewModel.itemLongEffect.set(null)
        super.onDestroyView()
    }

}