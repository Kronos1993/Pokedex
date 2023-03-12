package com.kronos.pokedex.ui.abilities

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kronos.core.adapters.AdapterItemClickListener
import com.kronos.core.adapters.diff.GeneralDiffCallback
import com.kronos.pokedex.databinding.ItemPokemonAbilityBinding
import com.kronos.pokedex.domian.model.ability.Ability
import com.kronos.webclient.UrlProvider

class PokemonAbilityAdapter : ListAdapter<Ability, PokemonAbilityAdapter.PokemonAbilityViewHolder>(GeneralDiffCallback<Ability>()) {

    private var adapterItemClickListener:AdapterItemClickListener<Ability>?=null

    fun setAdapterItemClick(adapterItemClickListener:AdapterItemClickListener<Ability>?){
        this.adapterItemClickListener = adapterItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonAbilityViewHolder {
        val binding = ItemPokemonAbilityBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PokemonAbilityViewHolder(binding,adapterItemClickListener)
    }

    override fun onBindViewHolder(holder: PokemonAbilityViewHolder, position: Int) {
        val currentPokemonType = getItemAt(position)
        holder.bind(currentPokemonType)
    }

    private fun getItemAt(adapterPosition: Int): Ability = getItem(adapterPosition)

    class PokemonAbilityViewHolder(var binding:ItemPokemonAbilityBinding, var clickListener:AdapterItemClickListener<Ability>?) : RecyclerView.ViewHolder(binding.root) {
        fun bind(ability: Ability){
            binding.run {
                pokemonAbility = ability
                root.setOnClickListener {
                    clickListener?.onItemClick(ability,adapterPosition)
                }
            }
        }
    }
}

