package com.kronos.pokedex.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kronos.core.adapters.AdapterItemClickListener
import com.kronos.core.adapters.diff.GeneralDiffCallback
import com.kronos.pokedex.databinding.ItemPokedexBinding
import com.kronos.pokedex.domian.model.ResponseListItem
import com.kronos.pokedex.domian.model.pokedex.Pokedex
import com.kronos.webclient.UrlProvider

class ItemListAdapter : ListAdapter<ResponseListItem, ItemListAdapter.ListItemViewHolder>(GeneralDiffCallback<ResponseListItem>()) {

    private var adapterItemClickListener:AdapterItemClickListener<ResponseListItem>?=null
    private lateinit var urlProvider: UrlProvider

    fun setAdapterItemClick(adapterItemClickListener:AdapterItemClickListener<ResponseListItem>?){
        this.adapterItemClickListener = adapterItemClickListener
    }

    fun setUrlProvider(urlProvider: UrlProvider){
        this.urlProvider = urlProvider
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        val binding = ItemPokedexBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ListItemViewHolder(binding,adapterItemClickListener)
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        val currentPokemon = getItemAt(position)
        holder.bind(currentPokemon,position)
    }

    private fun getItemAt(adapterPosition: Int): ResponseListItem = getItem(adapterPosition)

    class ListItemViewHolder(var binding:ItemPokedexBinding, var clickListener:AdapterItemClickListener<ResponseListItem>?) : RecyclerView.ViewHolder(binding.root) {
        fun bind(dex: ResponseListItem,position: Int){
            binding.run {
                pokedex = dex
                root.setOnClickListener {
                    clickListener?.onItemClick(dex,adapterPosition)
                }
            }
        }
    }
}
