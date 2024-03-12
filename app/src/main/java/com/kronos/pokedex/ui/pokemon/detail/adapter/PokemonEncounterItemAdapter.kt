package com.kronos.pokedex.ui.pokemon.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kronos.core.adapters.AdapterItemClickListener
import com.kronos.core.adapters.diff.GeneralDiffCallback
import com.kronos.pokedex.databinding.ItemPokemonEncounterBinding
import com.kronos.pokedex.domian.model.pokemon.Encounter
import com.kronos.webclient.UrlProvider

class PokemonEncounterItemAdapter : ListAdapter<Encounter, PokemonEncounterItemAdapter.PokemonEncounterViewHolder>(GeneralDiffCallback<Encounter>()) {

    private var adapterItemClickListener:AdapterItemClickListener<Encounter>?=null
    private lateinit var urlProvider: UrlProvider

    fun setAdapterItemClick(adapterItemClickListener:AdapterItemClickListener<Encounter>?){
        this.adapterItemClickListener = adapterItemClickListener
    }

    fun setUrlProvider(urlProvider: UrlProvider){
        this.urlProvider = urlProvider
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonEncounterViewHolder {
        val binding = ItemPokemonEncounterBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PokemonEncounterViewHolder(binding,adapterItemClickListener)
    }

    override fun onBindViewHolder(holder: PokemonEncounterViewHolder, position: Int) {
        val currentPokemonEncounter = getItemAt(position)
        holder.bind(currentPokemonEncounter)
        val encounterAdapter = EncounterVersionItemAdapter()
        encounterAdapter.submitList(currentPokemonEncounter.versionDetails)
        encounterAdapter.notifyItemRangeChanged(0,currentPokemonEncounter.versionDetails.size)
        holder.binding.recyclerViewVersionDetail.adapter = encounterAdapter
        holder.binding.recyclerViewVersionDetail.layoutManager = LinearLayoutManager(holder.binding.recyclerViewVersionDetail.context)
        holder.binding.recyclerViewVersionDetail.setHasFixedSize(false)
    }

    private fun getItemAt(adapterPosition: Int): Encounter = getItem(adapterPosition)

    class PokemonEncounterViewHolder(var binding:ItemPokemonEncounterBinding, var clickListener:AdapterItemClickListener<Encounter>?) : RecyclerView.ViewHolder(binding.root) {
        fun bind(encounter: Encounter){
            binding.run {
                pokemonEncounter = encounter
                root.setOnClickListener {
                    clickListener?.onItemClick(encounter,adapterPosition)
                }
            }
        }
    }
}

