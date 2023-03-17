package com.kronos.pokedex.ui.stats

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kronos.core.adapters.AdapterItemClickListener
import com.kronos.core.adapters.diff.GeneralDiffCallback
import com.kronos.pokedex.databinding.ItemPokemonStatBinding
import com.kronos.pokedex.domian.model.stat.Stat

class PokemonStatsAdapter : ListAdapter<Stat, PokemonStatsAdapter.PokemonStatViewHolder>(GeneralDiffCallback<Stat>()) {

    private var adapterItemClickListener:AdapterItemClickListener<Stat>?=null

    fun setAdapterItemClick(adapterItemClickListener:AdapterItemClickListener<Stat>?){
        this.adapterItemClickListener = adapterItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonStatViewHolder {
        val binding = ItemPokemonStatBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PokemonStatViewHolder(binding,adapterItemClickListener)
    }

    override fun onBindViewHolder(holder: PokemonStatViewHolder, position: Int) {
        val currentPokemonStat = getItemAt(position)
        holder.bind(currentPokemonStat)
    }

    private fun getItemAt(adapterPosition: Int): Stat = getItem(adapterPosition)

    class PokemonStatViewHolder(var binding:ItemPokemonStatBinding, var clickListener:AdapterItemClickListener<Stat>?) : RecyclerView.ViewHolder(binding.root) {
        fun bind(stat: Stat){
            binding.run {
                pokemonStat = stat
                root.setOnClickListener {
                    clickListener?.onItemClick(stat,adapterPosition)
                }
            }
        }
    }
}

