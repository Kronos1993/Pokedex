package com.kronos.pokedex.binding_adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.kronos.pokedex.R


@BindingAdapter("app:handle_pokemon_type")
fun handleType(view: ImageView, typeName: String) = view.run {
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
            typeName.equals("shadow", false) -> {
                view.setBackgroundResource(R.drawable.ic_pokemon_type_icons)
            }
            typeName.equals("unknown", false) -> {
                view.setBackgroundResource(R.drawable.ic_pokemon_type_icons)
            }
            else -> {
                view.setBackgroundResource(R.drawable.ic_pokemon_type_icons)
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
