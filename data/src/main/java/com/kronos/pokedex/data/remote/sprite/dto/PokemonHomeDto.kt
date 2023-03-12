package com.kronos.pokedex.data.remote.sprite.dto

import com.google.gson.annotations.SerializedName

data class PokemonHomeDto (
    @SerializedName("front_default")
    val frontHome:String = "",
    @SerializedName("front_shiny")
    val frontHomeShiny:String = ""
)