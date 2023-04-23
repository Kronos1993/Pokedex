package com.kronos.pokedex.ui.items.by_categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kronos.core.adapters.AdapterItemClickListener
import com.kronos.core.adapters.diff.GeneralDiffCallback
import com.kronos.pokedex.R
import com.kronos.pokedex.databinding.ItemCategoryItemBinding
import com.kronos.pokedex.databinding.ItemItemBinding
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.webclient.UrlProvider

class ItemByCategoriesListAdapter : ListAdapter<NamedResourceApi, ItemByCategoriesListAdapter.ItemByCategoriesListAdapter>(GeneralDiffCallback<NamedResourceApi>()) {

    private var adapterItemClickListener:AdapterItemClickListener<NamedResourceApi>?=null
    private lateinit var urlProvider: UrlProvider

    fun setAdapterItemClick(adapterItemClickListener:AdapterItemClickListener<NamedResourceApi>?){
        this.adapterItemClickListener = adapterItemClickListener
    }

    fun setUrlProvider(urlProvider: UrlProvider){
        this.urlProvider = urlProvider
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemByCategoriesListAdapter {
        val binding = ItemCategoryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ItemByCategoriesListAdapter(binding,adapterItemClickListener)
    }

    override fun onBindViewHolder(holder: ItemByCategoriesListAdapter, position: Int) {
        val currentItem = getItemAt(position)
        holder.bind(currentItem,position)
        Glide.with(holder.binding.imageViewItemCategory).load(urlProvider.getItemImageUrl(currentItem.name)).placeholder(
            R.drawable.ic_backpack).into(holder.binding.imageViewItemCategory)
    }

    private fun getItemAt(adapterPosition: Int): NamedResourceApi = getItem(adapterPosition)

    class ItemByCategoriesListAdapter(var binding:ItemCategoryItemBinding, var clickListener:AdapterItemClickListener<NamedResourceApi>?) : RecyclerView.ViewHolder(binding.root) {
        fun bind(currentItem: NamedResourceApi, position: Int){
            binding.run {
                item = currentItem
                root.setOnClickListener {
                    clickListener?.onItemClick(currentItem,adapterPosition)
                }
            }
        }
    }
}

