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

@BindingAdapter("app:handle_pokemon_growth_rate")
fun setPokemonGrowthRate(view: TextView,growthRate : String?) {
    view.run {
        if(growthRate!=null){
            var growthRateMod = growthRate.replace("-","")
            if(growthRateMod.length>1){
                view.text = growthRateMod.substring(0, 1).uppercase() + growthRateMod.substring(1).lowercase()
            }else{
                view.text = view.context.getString(R.string.unknown)
            }
        }
    }
}

@BindingAdapter("app:handle_pokemon_habitat")
fun setPokemonHabitat(view: TextView, habitat: String?) {
    view.run {
        if(habitat!=null){
            var habitatMod = habitat.replace("-"," ")
            if(habitatMod.length>1){
                view.text = habitatMod.substring(0, 1).uppercase() + habitatMod.substring(1).lowercase()
            }else{
                view.text = view.context.getString(R.string.unknown)
            }
        }
    }
}
