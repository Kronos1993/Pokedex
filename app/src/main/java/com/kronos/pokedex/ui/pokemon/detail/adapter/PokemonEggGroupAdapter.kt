package com.kronos.pokedex.ui.pokemon.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kronos.core.adapters.AdapterItemClickListener
import com.kronos.core.adapters.diff.GeneralDiffCallback
import com.kronos.pokedex.databinding.ItemEggGroupBinding
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.webclient.UrlProvider

class PokemonEggGroupAdapter : ListAdapter<NamedResourceApi, PokemonEggGroupAdapter.PokemonEggGroupViewHolder>(GeneralDiffCallback<NamedResourceApi>()) {

    private var adapterItemClickListener:AdapterItemClickListener<NamedResourceApi>?=null
    private lateinit var urlProvider: UrlProvider

    fun setAdapterItemClick(adapterItemClickListener:AdapterItemClickListener<NamedResourceApi>?){
        this.adapterItemClickListener = adapterItemClickListener
    }

    fun setUrlProvider(urlProvider: UrlProvider){
        this.urlProvider = urlProvider
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonEggGroupViewHolder {
        val binding = ItemEggGroupBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PokemonEggGroupViewHolder(binding,adapterItemClickListener)
    }

    override fun onBindViewHolder(holder: PokemonEggGroupViewHolder, position: Int) {
        val currentPokemonEggGroup = getItemAt(position)
        holder.bind(currentPokemonEggGroup)
    }

    private fun getItemAt(adapterPosition: Int): NamedResourceApi = getItem(adapterPosition)

    class PokemonEggGroupViewHolder(var binding:ItemEggGroupBinding, var clickListener:AdapterItemClickListener<NamedResourceApi>?) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pokemonEggGroup: NamedResourceApi){
            binding.run {
                eggGroup = pokemonEggGroup
                root.setOnClickListener {
                    clickListener?.onItemClick(pokemonEggGroup,adapterPosition)
                }
            }
        }
    }
}

