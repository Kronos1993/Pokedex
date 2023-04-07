package com.kronos.pokedex.ui.berries

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kronos.core.adapters.AdapterItemClickListener
import com.kronos.core.adapters.diff.GeneralDiffCallback
import com.kronos.pokedex.databinding.ItemBerryBinding
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.webclient.UrlProvider

class BerryListAdapter : ListAdapter<NamedResourceApi, BerryListAdapter.ListItemViewHolder>(GeneralDiffCallback<NamedResourceApi>()) {

    private var adapterItemClickListener:AdapterItemClickListener<NamedResourceApi>?=null
    private lateinit var urlProvider: UrlProvider

    fun setAdapterItemClick(adapterItemClickListener:AdapterItemClickListener<NamedResourceApi>?){
        this.adapterItemClickListener = adapterItemClickListener
    }

    fun setUrlProvider(urlProvider: UrlProvider){
        this.urlProvider = urlProvider
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        val binding = ItemBerryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ListItemViewHolder(binding,adapterItemClickListener)
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        val currentBerry = getItemAt(position)
        holder.bind(currentBerry,position)
    }

    private fun getItemAt(adapterPosition: Int): NamedResourceApi = getItem(adapterPosition)

    class ListItemViewHolder(var binding:ItemBerryBinding, var clickListener:AdapterItemClickListener<NamedResourceApi>?) : RecyclerView.ViewHolder(binding.root) {
        fun bind(berryItem: NamedResourceApi, position: Int){
            binding.run {
                berry = berryItem
                root.setOnClickListener {
                    clickListener?.onItemClick(berryItem,adapterPosition)
                }
            }
        }
    }
}

