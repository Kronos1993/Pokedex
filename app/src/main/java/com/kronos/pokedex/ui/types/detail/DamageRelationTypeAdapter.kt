package com.kronos.pokedex.ui.types.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kronos.core.adapters.AdapterItemClickListener
import com.kronos.core.adapters.diff.GeneralDiffCallback
import com.kronos.pokedex.databinding.ItemDamageRelationBinding
import com.kronos.pokedex.databinding.ItemTypeBinding
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.webclient.UrlProvider

class DamageRelationTypeAdapter : ListAdapter<DamageRelationContainer, DamageRelationTypeAdapter.DamageRelationContainerViewHolder>(GeneralDiffCallback<DamageRelationContainer>()) {

    private var adapterItemClickListener:AdapterItemClickListener<DamageRelationContainer>?=null
    private lateinit var urlProvider: UrlProvider

    fun setAdapterItemClick(adapterItemClickListener:AdapterItemClickListener<DamageRelationContainer>?){
        this.adapterItemClickListener = adapterItemClickListener
    }

    fun setUrlProvider(urlProvider: UrlProvider){
        this.urlProvider = urlProvider
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DamageRelationContainerViewHolder {
        val binding = ItemDamageRelationBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DamageRelationContainerViewHolder(binding,adapterItemClickListener)
    }

    override fun onBindViewHolder(holder: DamageRelationContainerViewHolder, position: Int) {
        val currentType = getItemAt(position)
        holder.bind(currentType)
    }

    private fun getItemAt(adapterPosition: Int): DamageRelationContainer = getItem(adapterPosition)

    class DamageRelationContainerViewHolder(var binding:ItemDamageRelationBinding, var clickListener:AdapterItemClickListener<DamageRelationContainer>?) : RecyclerView.ViewHolder(binding.root) {
        fun bind(currentDamageRelationType: DamageRelationContainer){
            binding.run {
                damageRelation = currentDamageRelationType
                root.setOnClickListener {
                    clickListener?.onItemClick(currentDamageRelationType,adapterPosition)
                }
            }
        }
    }
}

