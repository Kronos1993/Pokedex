package com.kronos.pokedex.binding_adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.kronos.pokedex.R


@BindingAdapter("app:handle_type")
fun handleType(view: ImageView, typeName: String?) = view.run {
    if (typeName != null) {
        when {
            typeName.equals("normal", false) -> {
                view.setBackgroundResource(R.drawable.ic_pokemon_type_normal)
            }
            typeName.equals("fighting", false) -> {
                view.setBackgroundResource(R.drawable.ic_pokemon_type_fighting)
            }
            typeName.equals("flying", false) -> {
                view.setBackgroundResource(R.drawable.ic_pokemon_type_flying)
            }
            typeName.equals("poison", false) -> {
                view.setBackgroundResource(R.drawable.ic_pokemon_type_poison)
            }
            typeName.equals("ground", false) -> {
                view.setBackgroundResource(R.drawable.ic_pokemon_type_ground)
            }
            typeName.equals("rock", false) -> {
                view.setBackgroundResource(R.drawable.ic_pokemon_type_rock)
            }
            typeName.equals("bug", false) -> {
                view.setBackgroundResource(R.drawable.ic_pokemon_type_bug)
            }
            typeName.equals("ghost", false) -> {
                view.setBackgroundResource(R.drawable.ic_pokemon_type_ghost)
            }
            typeName.equals("steel", false) -> {
                view.setBackgroundResource(R.drawable.ic_pokemon_type_steel)
            }
            typeName.equals("fire", false) -> {
                view.setBackgroundResource(R.drawable.ic_pokemon_type_fire)
            }
            typeName.equals("water", false) -> {
                view.setBackgroundResource(R.drawable.ic_pokemon_type_water)
            }
            typeName.equals("grass", false) -> {
                view.setBackgroundResource(R.drawable.ic_pokemon_type_grass)
            }
            typeName.equals("electric", false) -> {
                view.setBackgroundResource(R.drawable.ic_pokemon_type_electric)
            }
            typeName.equals("psychic", false) -> {
                view.setBackgroundResource(R.drawable.ic_pokemon_type_psychic)
            }
            typeName.equals("ice", false) -> {
                view.setBackgroundResource(R.drawable.ic_pokemon_type_ice)
            }
            typeName.equals("dragon", false) -> {
                view.setBackgroundResource(R.drawable.ic_pokemon_type_dragon)
            }
            typeName.equals("dark", false) -> {
                view.setBackgroundResource(R.drawable.ic_pokemon_type_dark)
            }
            typeName.equals("fairy", false) -> {
                view.setBackgroundResource(R.drawable.ic_pokemon_type_fairy)
            }
        }
    }
}

@BindingAdapter("app:handle_move_priority")
fun handleMovePriority(view: ImageView, typePriority: Int?) = view.run {
    if (typePriority != null) {
        when (typePriority) {
            1 -> {
                view.setBackgroundResource(R.drawable.ic_check)
            }
            0 -> {
                view.setBackgroundResource(R.drawable.ic_cancel)
            }
        }
    }
}

@BindingAdapter("app:handle_egg_group")
fun handleEggGroup(view: ImageView, eggGroup: String?) = view.run {
    if (eggGroup != null) {
        when (eggGroup) {
            "monster" -> {
                view.setBackgroundResource(R.drawable.ic_pokemon_type_dragon)
            }
            "water1" -> {
                view.setBackgroundResource(R.drawable.ic_pokemon_type_water)
            }
            "bug" -> {
                view.setBackgroundResource(R.drawable.ic_pokemon_type_bug)
            }
            "flying" -> {
                view.setBackgroundResource(R.drawable.ic_pokemon_type_flying)
            }
            "ground" -> {
                view.setBackgroundResource(R.drawable.ic_pokemon_type_ground)
            }
            "fairy" -> {
                view.setBackgroundResource(R.drawable.ic_pokemon_type_fairy)
            }
            "plant" -> {
                view.setBackgroundResource(R.drawable.ic_pokemon_type_grass)
            }
            "humanshape" -> {
                view.setBackgroundResource(R.drawable.ic_pokemon_human_shape)
            }
            "water3" -> {
                view.setBackgroundResource(R.drawable.ic_pokemon_type_water)
            }
            "mineral" -> {
                view.setBackgroundResource(R.drawable.ic_mineral)
            }
            "indeterminate" -> {
                view.setBackgroundResource(R.drawable.ic_pokemon_egg)
            }
            "water2" -> {
                view.setBackgroundResource(R.drawable.ic_pokemon_type_water)
            }
            "ditto" -> {
                view.setBackgroundResource(R.drawable.ic_pokemon_egg)
            }
            "dragon" -> {
                view.setBackgroundResource(R.drawable.ic_pokemon_type_dragon)
            }
            "no-eggs" -> {
                view.setBackgroundResource(R.drawable.ic_pokemon_no_egg)
            }
        }
    }
}

@BindingAdapter("app:handle_pokemon_sprite")
fun handlePokemonSprite(view: ImageView, spriteUrl: String){
    if (spriteUrl != null) {
        Glide.with(view.context).load(spriteUrl).placeholder(R.drawable.ic_pokeball).into(view)
    }
}

@BindingAdapter("app:handle_item_sprite")
fun handleItemSprite(view: ImageView, itemSpriteUrl: String?){
    if (itemSpriteUrl != null) {
        Glide.with(view.context).load(itemSpriteUrl).placeholder(R.drawable.ic_backpack).into(view)
    }
}

@BindingAdapter("app:handle_pokemon_gender")
fun handlePokemonGender(view: ImageView, pokemon_gender: Int?){
    view.run {
        if(pokemon_gender!=null){
            if(pokemon_gender == 1)
                view.setBackgroundResource(R.drawable.ic_female)
            else
                view.setBackgroundResource(R.drawable.ic_male)
        }
    }
}

@BindingAdapter("app:handle_pokemon_evol_time_of_day")
fun handlePokemonEvolTime(view: ImageView, pokemon_evol_time: String?){
    view.run {
        if(pokemon_evol_time!=null){
            if(pokemon_evol_time == "day")
                view.setBackgroundResource(R.drawable.ic_day)
            else if(pokemon_evol_time == "night")
                view.setBackgroundResource(R.drawable.ic_night)
            else if(pokemon_evol_time == "full-moon")
                view.setBackgroundResource(R.drawable.ic_full_moon)
        }
    }
}

@BindingAdapter("app:handle_pokemon_move_learned_method")
fun handlePokemonMoveLearnedMethod(view: ImageView, learnedMethod: String?){
    view.run {
        if(learnedMethod!=null){
            when(learnedMethod){
                "egg"->{
                    view.setBackgroundResource(R.drawable.ic_pokemon_egg)
                }
                "tutor"->{
                    view.setBackgroundResource(R.drawable.ic_tutor)
                }
                "level-up"->{
                    view.setBackgroundResource(R.drawable.ic_level)
                }
                "machine"->{
                    view.setBackgroundResource(R.drawable.ic_move)
                }
            }
        }
    }
}
