package com.kronos.pokedex.ui.move.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kronos.core.adapters.AdapterItemClickListener
import com.kronos.core.adapters.diff.GeneralDiffCallback
import com.kronos.pokedex.databinding.ItemPokemonMoveBinding
import com.kronos.pokedex.domian.model.move.MoveList

class PokemonMoveListAdapter : ListAdapter<MoveList, PokemonMoveListAdapter.PokemonMoveListViewHolder>(GeneralDiffCallback<MoveList>()) {

    private var adapterItemClickListener: AdapterItemClickListener<MoveList>? = null

    fun setAdapterItemClick(adapterItemClickListener: AdapterItemClickListener<MoveList>?) {
        this.adapterItemClickListener = adapterItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonMoveListViewHolder {
        val binding =
            ItemPokemonMoveBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonMoveListViewHolder(binding, adapterItemClickListener)
    }

    override fun onBindViewHolder(holder: PokemonMoveListViewHolder, position: Int) {
        val currentPokemon = getItemAt(position)
        holder.bind(currentPokemon, position)
    }

    private fun getItemAt(adapterPosition: Int): MoveList = getItem(adapterPosition)

    class PokemonMoveListViewHolder(
        var binding: ItemPokemonMoveBinding,
        var clickListener: AdapterItemClickListener<MoveList>?
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(move: MoveList, position: Int) {
            var level = move.moveDetails.run {
                if(this.isNotEmpty())
                    this[0].levelLearned
                else{
                    0
                }
            }
            binding.run {
                pokemonMove = move
                pokemonMoveLevelLearned = level
                root.setOnClickListener {
                    clickListener?.onItemClick(pokemonMove, adapterPosition)
                }
            }
        }
    }
}

