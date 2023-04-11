package com.kronos.pokedex.ui.berries.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kronos.core.adapters.AdapterItemClickListener
import com.kronos.core.adapters.diff.GeneralDiffCallback
import com.kronos.pokedex.R
import com.kronos.pokedex.databinding.ItemBerryFlavorBinding
import com.kronos.pokedex.databinding.ItemPokemonBinding
import com.kronos.pokedex.domian.model.item.BerryFlavor
import com.kronos.pokedex.domian.model.pokemon.PokemonDexEntry
import com.kronos.webclient.UrlProvider

class BerryFlavorListAdapter : ListAdapter<BerryFlavor, BerryFlavorListAdapter.BerryFlavorListViewHolder>(GeneralDiffCallback<BerryFlavor>()) {

    private var adapterItemClickListener:AdapterItemClickListener<BerryFlavor>?=null
    private lateinit var urlProvider: UrlProvider

    fun setAdapterItemClick(adapterItemClickListener:AdapterItemClickListener<BerryFlavor>?){
        this.adapterItemClickListener = adapterItemClickListener
    }

    fun setUrlProvider(urlProvider: UrlProvider){
        this.urlProvider = urlProvider
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BerryFlavorListViewHolder {
        val binding = ItemBerryFlavorBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BerryFlavorListViewHolder(binding,adapterItemClickListener)
    }

    override fun onBindViewHolder(holder: BerryFlavorListViewHolder, position: Int) {
        val currentPokemon = getItemAt(position)
        holder.bind(currentPokemon,position)
    }

    private fun getItemAt(adapterPosition: Int): BerryFlavor = getItem(adapterPosition)

    class BerryFlavorListViewHolder(var binding:ItemBerryFlavorBinding, var clickListener:AdapterItemClickListener<BerryFlavor>?) : RecyclerView.ViewHolder(binding.root) {
        fun bind(berryFlavor: BerryFlavor,position: Int){
            binding.run {
                flavor = berryFlavor
                root.setOnClickListener {
                    clickListener?.onItemClick(berryFlavor,adapterPosition)
                }
            }
        }
    }
}

