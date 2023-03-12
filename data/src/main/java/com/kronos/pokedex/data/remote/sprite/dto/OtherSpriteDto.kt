package com.kronos.pokedex.data.remote.sprite.dto

import com.google.gson.annotations.SerializedName

data class OtherSpriteDto(
    @SerializedName("home")
    val home:PokemonHomeDto = PokemonHomeDto(),
)