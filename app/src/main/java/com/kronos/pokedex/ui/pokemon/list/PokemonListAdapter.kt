package com.kronos.pokedex.ui.pokemon.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kronos.core.adapters.AdapterItemClickListener
import com.kronos.core.adapters.diff.GeneralDiffCallback
import com.kronos.pokedex.databinding.ItemPokemonBinding
import com.kronos.pokedex.domian.model.pokemon.PokemonDexEntry
import com.kronos.pokedex.domian.model.pokemon.PokemonList
import com.kronos.webclient.UrlProvider

class PokemonListAdapter : ListAdapter<PokemonDexEntry, PokemonListAdapter.PokemonListViewHolder>(GeneralDiffCallback<PokemonDexEntry>()) {

    private var adapterItemClickListener:AdapterItemClickListener<PokemonDexEntry>?=null
    private lateinit var urlProvider: UrlProvider

    fun setAdapterItemClick(adapterItemClickListener:AdapterItemClickListener<PokemonDexEntry>?){
        this.adapterItemClickListener = adapterItemClickListener
    }

    fun setUrlProvider(urlProvider: UrlProvider){
        this.urlProvider = urlProvider
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonListViewHolder {
        val binding = ItemPokemonBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PokemonListViewHolder(binding,adapterItemClickListener)
    }

    override fun onBindViewHolder(holder: PokemonListViewHolder, position: Int) {
        val currentPokemon = getItemAt(position)
        holder.bind(currentPokemon,position)
        val dexNumber = currentPokemon.pokemon.url.replace("https://pokeapi.co/api/v2/pokemon-species/","").replace("/","")
        Glide.with(holder.binding.imageViewPokemonItem).load(urlProvider.getImageUrl(Integer.valueOf(dexNumber))).into(holder.binding.imageViewPokemonItem)
    }

    private fun getItemAt(adapterPosition: Int): PokemonDexEntry = getItem(adapterPosition)

    class PokemonListViewHolder(var binding:ItemPokemonBinding, var clickListener:AdapterItemClickListener<PokemonDexEntry>?) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pokemon: PokemonDexEntry,position: Int){
            binding.run {
                pokemonDexEntryModel = pokemon
                root.setOnClickListener {
                    clickListener?.onItemClick(pokemon,adapterPosition)
                }
            }
        }
    }
}

