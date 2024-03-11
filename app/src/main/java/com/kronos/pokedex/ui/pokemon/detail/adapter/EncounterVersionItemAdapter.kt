package com.kronos.pokedex.ui.pokemon.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kronos.core.adapters.AdapterItemClickListener
import com.kronos.core.adapters.diff.GeneralDiffCallback
import com.kronos.pokedex.databinding.ItemVersionDetailBinding
import com.kronos.pokedex.domian.model.pokemon.VersionDetail
import com.kronos.webclient.UrlProvider

class EncounterVersionItemAdapter : ListAdapter<VersionDetail, EncounterVersionItemAdapter.EncounterViewHolder>(GeneralDiffCallback<VersionDetail>()) {

    private var adapterItemClickListener:AdapterItemClickListener<VersionDetail>?=null
    private lateinit var urlProvider: UrlProvider

    fun setAdapterItemClick(adapterItemClickListener:AdapterItemClickListener<VersionDetail>?){
        this.adapterItemClickListener = adapterItemClickListener
    }

    fun setUrlProvider(urlProvider: UrlProvider){
        this.urlProvider = urlProvider
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EncounterViewHolder {
        val binding = ItemVersionDetailBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return EncounterViewHolder(binding,adapterItemClickListener)
    }

    override fun onBindViewHolder(holder: EncounterViewHolder, position: Int) {
        val currentPokemonEncounter = getItemAt(position)
        holder.bind(currentPokemonEncounter)
    }

    private fun getItemAt(adapterPosition: Int): VersionDetail = getItem(adapterPosition)

    class EncounterViewHolder(var binding:ItemVersionDetailBinding, var clickListener:AdapterItemClickListener<VersionDetail>?) : RecyclerView.ViewHolder(binding.root) {
        fun bind(version: VersionDetail){
            binding.run {
                versionDetail = version
                root.setOnClickListener {
                    clickListener?.onItemClick(version,adapterPosition)
                }
            }
        }
    }
}

