package com.kronos.pokedex.ui.pokemon.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kronos.core.adapters.AdapterItemClickListener
import com.kronos.core.adapters.diff.GeneralDiffCallback
import com.kronos.pokedex.databinding.ItemPokemonBinding
import com.kronos.pokedex.databinding.ItemPokemonSpriteBinding
import com.kronos.pokedex.databinding.ItemPokemonTypeBinding
import com.kronos.pokedex.domian.model.pokemon.PokemonList
import com.kronos.pokedex.domian.model.type.Type
import com.kronos.webclient.UrlProvider

class PokemonSpriteAdapter : ListAdapter<String, PokemonSpriteAdapter.PokemonSpriteViewHolder>(GeneralDiffCallback<String>()) {

    private var adapterItemClickListener:AdapterItemClickListener<String>?=null

    fun setAdapterItemClick(adapterItemClickListener:AdapterItemClickListener<String>?){
        this.adapterItemClickListener = adapterItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonSpriteViewHolder {
        val binding = ItemPokemonSpriteBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PokemonSpriteViewHolder(binding,adapterItemClickListener)
    }

    override fun onBindViewHolder(holder: PokemonSpriteViewHolder, position: Int) {
        val currentPokemonType = getItemAt(position)
        holder.bind(currentPokemonType)
    }

    private fun getItemAt(adapterPosition: Int): String = getItem(adapterPosition)

    class PokemonSpriteViewHolder(var binding:ItemPokemonSpriteBinding, var clickListener:AdapterItemClickListener<String>?) : RecyclerView.ViewHolder(binding.root) {
        fun bind(spriteUrl: String){
            binding.run {
                pokemonSpriteUrl = spriteUrl
                root.setOnClickListener {
                    clickListener?.onItemClick(spriteUrl,adapterPosition)
                }
            }
        }
    }
}

