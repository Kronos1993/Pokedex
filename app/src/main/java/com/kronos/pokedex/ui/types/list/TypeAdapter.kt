package com.kronos.pokedex.ui.types.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kronos.core.adapters.AdapterItemClickListener
import com.kronos.core.adapters.diff.GeneralDiffCallback
import com.kronos.pokedex.databinding.ItemEggGroupBinding
import com.kronos.pokedex.databinding.ItemTypeBinding
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.webclient.UrlProvider

class TypeAdapter : ListAdapter<NamedResourceApi, TypeAdapter.TypeViewHolder>(GeneralDiffCallback<NamedResourceApi>()) {

    private var adapterItemClickListener:AdapterItemClickListener<NamedResourceApi>?=null
    private lateinit var urlProvider: UrlProvider

    fun setAdapterItemClick(adapterItemClickListener:AdapterItemClickListener<NamedResourceApi>?){
        this.adapterItemClickListener = adapterItemClickListener
    }

    fun setUrlProvider(urlProvider: UrlProvider){
        this.urlProvider = urlProvider
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeViewHolder {
        val binding = ItemTypeBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TypeViewHolder(binding,adapterItemClickListener)
    }

    override fun onBindViewHolder(holder: TypeViewHolder, position: Int) {
        val currentType = getItemAt(position)
        holder.bind(currentType)
    }

    private fun getItemAt(adapterPosition: Int): NamedResourceApi = getItem(adapterPosition)

    class TypeViewHolder(var binding:ItemTypeBinding, var clickListener:AdapterItemClickListener<NamedResourceApi>?) : RecyclerView.ViewHolder(binding.root) {
        fun bind(currentType: NamedResourceApi){
            binding.run {
                type = currentType
                root.setOnClickListener {
                    clickListener?.onItemClick(currentType,adapterPosition)
                }
            }
        }
    }
}

