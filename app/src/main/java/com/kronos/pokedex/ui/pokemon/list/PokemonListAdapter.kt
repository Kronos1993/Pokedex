package com.kronos.pokedex.ui.pokemon.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kronos.core.adapters.AdapterItemClickListener
import com.kronos.core.adapters.diff.GeneralDiffCallback
import com.kronos.pokedex.databinding.ItemPokemonBinding
import com.kronos.pokedex.domian.model.pokemon.PokemonList
import com.kronos.webclient.UrlProvider

class PokemonListAdapter : ListAdapter<PokemonList, PokemonListAdapter.PokemonListViewHolder>(GeneralDiffCallback<PokemonList>()) {

    private var adapterItemClickListener:AdapterItemClickListener<PokemonList>?=null
    private lateinit var urlProvider: UrlProvider

    fun setAdapterItemClick(adapterItemClickListener:AdapterItemClickListener<PokemonList>?){
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
        Glide.with(holder.binding.imageViewPokemonItem).load(urlProvider.getImageUrl(position +1)).into(holder.binding.imageViewPokemonItem)
    }

    private fun getItemAt(adapterPosition: Int): PokemonList = getItem(adapterPosition)

    class PokemonListViewHolder(var binding:ItemPokemonBinding, var clickListener:AdapterItemClickListener<PokemonList>?) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pokemon: PokemonList,position: Int){
            binding.run {
                pokemonListModel = pokemon
                pokedexNumber = position + 1
                root.setOnClickListener {
                    clickListener?.onItemClick(pokemon,adapterPosition)
                }
            }
        }
    }
}

