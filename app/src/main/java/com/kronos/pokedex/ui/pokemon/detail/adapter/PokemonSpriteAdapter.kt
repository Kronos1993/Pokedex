package com.kronos.pokedex.ui.pokemon.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kronos.core.adapters.AdapterItemClickListener
import com.kronos.core.adapters.diff.GeneralDiffCallback
import com.kronos.pokedex.R
import com.kronos.pokedex.databinding.ItemPokemonSpriteBinding
import com.kronos.webclient.UrlProvider

class PokemonSpriteAdapter : ListAdapter<Pair<String,String>, PokemonSpriteAdapter.PokemonSpriteViewHolder>(GeneralDiffCallback<Pair<String,String>>()) {

    private var adapterItemClickListener:AdapterItemClickListener<Pair<String,String>>?=null

    private lateinit var urlProvider: UrlProvider

    fun setAdapterItemClick(adapterItemClickListener:AdapterItemClickListener<Pair<String,String>>?){
        this.adapterItemClickListener = adapterItemClickListener
    }

    fun setUrlProvider(urlProvider: UrlProvider){
        this.urlProvider = urlProvider
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonSpriteViewHolder {
        val binding = ItemPokemonSpriteBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PokemonSpriteViewHolder(binding,adapterItemClickListener)
    }

    override fun onBindViewHolder(holder: PokemonSpriteViewHolder, position: Int) {
        val currentPokemonType = getItemAt(position)
        holder.bind(currentPokemonType)
        if (currentPokemonType.first != null) {
            try {
                Glide.with(holder.binding.imageViewSprite.context).load(urlProvider.getImageUrl(urlProvider.extractIdFromUrl(currentPokemonType.first))).placeholder(R.drawable.ic_pokeball).into(holder.binding.imageViewSprite)
            }catch (e:Exception){
                e.printStackTrace()
                Glide.with(holder.binding.imageViewSprite.context).load(currentPokemonType.first).placeholder(R.drawable.ic_pokeball).into(holder.binding.imageViewSprite)
            }
        }
    }

    private fun getItemAt(adapterPosition: Int): Pair<String,String> = getItem(adapterPosition)

    class PokemonSpriteViewHolder(var binding:ItemPokemonSpriteBinding, var clickListener:AdapterItemClickListener<Pair<String,String>>?) : RecyclerView.ViewHolder(binding.root) {
        fun bind(sprite: Pair<String,String>){
            binding.run {
                pokemonSprite = sprite
                root.setOnClickListener {
                    clickListener?.onItemClick(sprite,adapterPosition)
                }
            }
        }
    }
}

