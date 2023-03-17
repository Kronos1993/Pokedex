package com.kronos.pokedex.ui.pokemon.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kronos.core.adapters.AdapterItemClickListener
import com.kronos.core.adapters.diff.GeneralDiffCallback
import com.kronos.pokedex.databinding.ItemPokemonEvolutionChainBinding
import com.kronos.pokedex.databinding.ItemPokemonSpriteBinding
import com.kronos.pokedex.domian.model.evolution_chain.ChainLink
import com.kronos.pokedex.domian.model.evolution_chain.EvolutionDetail
import com.kronos.webclient.UrlProvider

class PokemonEvolutionChainAdapter : ListAdapter<ChainLink, PokemonEvolutionChainAdapter.PokemonEvolutionChainViewHolder>(GeneralDiffCallback<ChainLink>()) {

    private var adapterItemClickListener:AdapterItemClickListener<ChainLink>?=null
    private lateinit var urlProvider: UrlProvider

    fun setAdapterItemClick(adapterItemClickListener:AdapterItemClickListener<ChainLink>?){
        this.adapterItemClickListener = adapterItemClickListener
    }

    fun setUrlProvider(urlProvider: UrlProvider){
        this.urlProvider = urlProvider
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonEvolutionChainViewHolder {
        val binding = ItemPokemonEvolutionChainBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PokemonEvolutionChainViewHolder(binding,adapterItemClickListener)
    }

    override fun onBindViewHolder(holder: PokemonEvolutionChainViewHolder, position: Int) {
        val currentPokemonEvolutionChain = getItemAt(position)
        var evoDetail = EvolutionDetail()
        if(currentPokemonEvolutionChain.evolutionDetails.isNotEmpty())
            evoDetail = currentPokemonEvolutionChain.evolutionDetails[0]
        holder.bind(currentPokemonEvolutionChain,urlProvider.getImageUrl(urlProvider.extractIdFromUrl(currentPokemonEvolutionChain.species.url)),evoDetail)
    }

    private fun getItemAt(adapterPosition: Int): ChainLink = getItem(adapterPosition)

    class PokemonEvolutionChainViewHolder(var binding:ItemPokemonEvolutionChainBinding, var clickListener:AdapterItemClickListener<ChainLink>?) : RecyclerView.ViewHolder(binding.root) {
        fun bind(chain: ChainLink,imageUrl:String,evoDetail:EvolutionDetail){
            binding.run {
                evolutionChain = chain
                evolutionSprite = imageUrl
                evolutionDetail = evoDetail
                root.setOnClickListener {
                    clickListener?.onItemClick(chain,adapterPosition)
                }
            }
        }
    }
}

