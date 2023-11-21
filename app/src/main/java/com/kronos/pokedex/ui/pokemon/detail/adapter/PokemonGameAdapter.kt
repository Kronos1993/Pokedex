package com.kronos.pokedex.ui.pokemon.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kronos.core.adapters.AdapterItemClickListener
import com.kronos.core.adapters.diff.GeneralDiffCallback
import com.kronos.pokedex.databinding.ItemPokemonGameBinding
import com.kronos.pokedex.domian.model.game.Game
import com.kronos.webclient.UrlProvider

class PokemonGameAdapter : ListAdapter<Game, PokemonGameAdapter.PokemonGameViewHolder>(GeneralDiffCallback<Game>()) {

    private var adapterItemClickListener:AdapterItemClickListener<Game>?=null
    private lateinit var urlProvider: UrlProvider

    fun setAdapterItemClick(adapterItemClickListener:AdapterItemClickListener<Game>?){
        this.adapterItemClickListener = adapterItemClickListener
    }

    fun setUrlProvider(urlProvider: UrlProvider){
        this.urlProvider = urlProvider
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonGameViewHolder {
        val binding = ItemPokemonGameBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PokemonGameViewHolder(binding,adapterItemClickListener)
    }

    override fun onBindViewHolder(holder: PokemonGameViewHolder, position: Int) {
        val currentPokemonGame = getItemAt(position)
        holder.bind(currentPokemonGame)
    }

    private fun getItemAt(adapterPosition: Int): Game = getItem(adapterPosition)

    class PokemonGameViewHolder(var binding:ItemPokemonGameBinding, var clickListener:AdapterItemClickListener<Game>?) : RecyclerView.ViewHolder(binding.root) {
        fun bind(game: Game){
            binding.run {
                pokemonGame = game
                root.setOnClickListener {
                    clickListener?.onItemClick(game,adapterPosition)
                }
            }
        }
    }
}

