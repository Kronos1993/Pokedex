package com.kronos.pokedex.ui.types

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kronos.core.adapters.AdapterItemClickListener
import com.kronos.core.adapters.diff.GeneralDiffCallback
import com.kronos.pokedex.databinding.ItemPokemonTypeBinding
import com.kronos.pokedex.domian.model.type.Type
import com.kronos.webclient.UrlProvider

class PokemonTypeAdapter : ListAdapter<Type, PokemonTypeAdapter.PokemonTypeViewHolder>(GeneralDiffCallback<Type>()) {

    private var adapterItemClickListener:AdapterItemClickListener<Type>?=null
    private lateinit var urlProvider: UrlProvider

    fun setAdapterItemClick(adapterItemClickListener:AdapterItemClickListener<Type>?){
        this.adapterItemClickListener = adapterItemClickListener
    }

    fun setUrlProvider(urlProvider: UrlProvider){
        this.urlProvider = urlProvider
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonTypeViewHolder {
        val binding = ItemPokemonTypeBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PokemonTypeViewHolder(binding,adapterItemClickListener)
    }

    override fun onBindViewHolder(holder: PokemonTypeViewHolder, position: Int) {
        val currentPokemonType = getItemAt(position)
        holder.bind(currentPokemonType)
    }

    private fun getItemAt(adapterPosition: Int): Type = getItem(adapterPosition)

    class PokemonTypeViewHolder(var binding:ItemPokemonTypeBinding, var clickListener:AdapterItemClickListener<Type>?) : RecyclerView.ViewHolder(binding.root) {
        fun bind(type: Type){
            binding.run {
                pokemonType = type
                root.setOnClickListener {
                    clickListener?.onItemClick(type,adapterPosition)
                }
            }
        }
    }
}

