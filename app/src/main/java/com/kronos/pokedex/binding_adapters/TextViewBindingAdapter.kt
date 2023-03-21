package com.kronos.pokedex.binding_adapters

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.kronos.pokedex.R
import com.kronos.pokedex.domian.model.specie.SpecieInfo
import java.util.*

@BindingAdapter("app:set_stat_name")
fun setStatName(view: TextView, stat: String) {
    view.run {
        when {
            stat.equals("hp",false) -> {
                view.text = view.context.getString(R.string.hp)
            }
            stat.equals("attack",false) -> {
                view.text = view.context.getString(R.string.attack)
            }
            stat.equals("defense",false) -> {
                view.text = view.context.getString(R.string.defense)
            }
            stat.equals("special-attack",false) -> {
                view.text = view.context.getString(R.string.special_attack)
            }
            stat.equals("special-defense",false) -> {
                view.text = view.context.getString(R.string.special_defense)
            }
            stat.equals("speed",false) -> {
                view.text = view.context.getString(R.string.speed)
            }
        }
    }
}

@BindingAdapter("app:pokemon_type")
fun setPokemonType(view: TextView, specie: SpecieInfo?) {
    view.run {
        visibility = View.VISIBLE
        if(specie!=null){
            when {
                specie.isBaby -> {
                    view.text = view.context.getString(R.string.baby_pokemon)
                }
                specie.isLegendary -> {
                    view.text = view.context.getString(R.string.legendary_pokemon)
                }
                specie.isMythical -> {
                    view.text = view.context.getString(R.string.mythical_pokemon)
                }
                else->{
                    visibility = View.GONE
                }
            }
        }
    }
}

@BindingAdapter("app:handle_text")
fun transformText(view: TextView,text : String?) {
    view.run {
        if(text!=null){
            var textMod = text.replace(Regex("-")," ")
            if(textMod.length>1){
                view.text = textMod.substring(0, 1).uppercase() + textMod.substring(1).lowercase()
            }else{
                view.text = textMod
            }
        }else{
            view.text = view.context.getString(R.string.unknown)
        }
    }
}

@BindingAdapter("app:handle_pokemon_move_name")
fun setPokemonMoveName(view: TextView, moveName: String?) {
    view.run {
        if(moveName!=null){
            var move = moveName.replace(Regex("-")," ")
            if(move.length>1){
                view.text = move.substring(0, 1).uppercase() + move.substring(1).lowercase()
            }else{
                view.text = view.context.getString(R.string.unknown)
            }
        }
    }
}

@BindingAdapter("app:handle_pokemon_name")
fun setPokemonName(view: TextView, pokemonName: String?) {
    view.run {
        if(pokemonName!=null){
            var name = pokemonName.replace(Regex("-")," ")
            if(name.length>1){
                view.text = name.substring(0, 1).uppercase() + name.substring(1).lowercase()
            }else{
                view.text = view.context.getString(R.string.unknown)
            }
        }
    }
}

@BindingAdapter("app:handle_pokedex_name")
fun setPokedexName(view: TextView, pokedexName: String?) {
    view.run {
        if(pokedexName!=null){
            var name = pokedexName.replace(Regex("updated"),"").replace(Regex("extended"),"").replace(Regex("-")," ")
            if(name.length>1){
                view.text = name.uppercase()
            }else{
                view.text = view.context.getString(R.string.unknown)
            }
        }
    }
}

@BindingAdapter("app:handle_pokemon_height")
fun setPokemonHeight(view: TextView, height: Double?) {
    view.run {
        if(height!=null){
            view.text = "${(height/10)} mts"
        }
    }
}

@BindingAdapter("app:handle_pokemon_weight")
fun setPokemonWeight(view: TextView, weight: Double?) {
    view.run {
        if(weight!=null){
            view.text = "${(weight/10)} kg"
        }
    }
}