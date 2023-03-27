package com.kronos.pokedex.ui.move

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kronos.core.adapters.AdapterItemClickListener
import com.kronos.core.adapters.diff.GeneralDiffCallback
import com.kronos.pokedex.databinding.ItemMoveListBinding
import com.kronos.pokedex.databinding.ItemPokemonMoveBinding
import com.kronos.pokedex.domian.model.move.MoveList

class PokemonMoveListAdapter(var showIn: ShowMoveIn = ShowMoveIn.MOVE_LIST) :
    ListAdapter<MoveList, RecyclerView.ViewHolder>(GeneralDiffCallback<MoveList>()) {

    private var adapterItemClickListener: AdapterItemClickListener<MoveList>? = null

    fun setAdapterItemClick(adapterItemClickListener: AdapterItemClickListener<MoveList>?) {
        this.adapterItemClickListener = adapterItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (showIn == ShowMoveIn.MOVE_LIST) {
            val binding =
                ItemMoveListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            MoveListViewHolder(binding, adapterItemClickListener)
        } else {
            val binding =
                ItemPokemonMoveBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            PokemonMoveListViewHolder(binding, adapterItemClickListener)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentPokemon = getItemAt(position)
        if (holder is PokemonMoveListViewHolder)
            holder.bind(currentPokemon)
        else if (holder is MoveListViewHolder)
            holder.bind(currentPokemon)
    }

    private fun getItemAt(adapterPosition: Int): MoveList = getItem(adapterPosition)

    class PokemonMoveListViewHolder(
        var binding: ItemPokemonMoveBinding,
        var clickListener: AdapterItemClickListener<MoveList>?
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(move: MoveList) {
            var level = move.moveDetails.let {
                if (it.isNotEmpty())
                    it[0].levelLearned
                else {
                    0
                }
            }
            binding.run {
                pokemonMove = move
                pokemonMoveLevelLearned = level
                root.setOnClickListener {
                    clickListener?.onItemClick(move, adapterPosition)
                }
            }
        }
    }

    class MoveListViewHolder(
        var binding: ItemMoveListBinding,
        var clickListener: AdapterItemClickListener<MoveList>?
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(move: MoveList) {
            binding.run {
                pokemonMove = move
                root.setOnClickListener {
                    clickListener?.onItemClick(move, adapterPosition)
                }
            }
        }
    }
}

