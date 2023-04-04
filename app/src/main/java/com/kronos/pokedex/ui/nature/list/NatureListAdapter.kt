package com.kronos.pokedex.ui.nature.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kronos.core.adapters.AdapterItemClickListener
import com.kronos.core.adapters.diff.GeneralDiffCallback
import com.kronos.pokedex.databinding.ItemNatureBinding
import com.kronos.pokedex.databinding.ItemPokedexBinding
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.webclient.UrlProvider

class NatureListAdapter : ListAdapter<NamedResourceApi, NatureListAdapter.ListItemViewHolder>(GeneralDiffCallback<NamedResourceApi>()) {

    private var adapterItemClickListener:AdapterItemClickListener<NamedResourceApi>?=null
    private lateinit var urlProvider: UrlProvider

    fun setAdapterItemClick(adapterItemClickListener:AdapterItemClickListener<NamedResourceApi>?){
        this.adapterItemClickListener = adapterItemClickListener
    }

    fun setUrlProvider(urlProvider: UrlProvider){
        this.urlProvider = urlProvider
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        val binding = ItemNatureBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ListItemViewHolder(binding,adapterItemClickListener)
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        val currentPokemon = getItemAt(position)
        holder.bind(currentPokemon)
    }

    private fun getItemAt(adapterPosition: Int): NamedResourceApi = getItem(adapterPosition)

    class ListItemViewHolder(var binding:ItemNatureBinding, var clickListener:AdapterItemClickListener<NamedResourceApi>?) : RecyclerView.ViewHolder(binding.root) {
        fun bind(dex: NamedResourceApi){
            binding.run {
                nature = dex
                root.setOnClickListener {
                    clickListener?.onItemClick(dex,adapterPosition)
                }
            }
        }
    }
}

