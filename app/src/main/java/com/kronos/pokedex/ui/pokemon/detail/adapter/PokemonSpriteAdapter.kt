package com.kronos.pokedex.ui.pokemon.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kronos.core.adapters.AdapterItemClickListener
import com.kronos.core.adapters.diff.GeneralDiffCallback
import com.kronos.pokedex.databinding.ItemPokemonSpriteBinding

class PokemonSpriteAdapter : ListAdapter<Pair<String,String>, PokemonSpriteAdapter.PokemonSpriteViewHolder>(GeneralDiffCallback<Pair<String,String>>()) {

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

    private fun getItemAt(adapterPosition: Int): Pair<String,String> = getItem(adapterPosition)

    class PokemonSpriteViewHolder(var binding:ItemPokemonSpriteBinding, var clickListener:AdapterItemClickListener<String>?) : RecyclerView.ViewHolder(binding.root) {
        fun bind(sprite: Pair<String,String>){
            binding.run {
                pokemonSprite = sprite
                root.setOnClickListener {
                    clickListener?.onItemClick(sprite.first,adapterPosition)
                }
            }
        }
    }
}

