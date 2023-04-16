package com.kronos.pokedex.data.remote.ability.dto

import com.google.gson.annotations.SerializedName
import com.kronos.pokedex.data.remote.response_list.NamedResourceApiDto
import java.io.Serializable

data class PokemonWithAbilityDto(
    @SerializedName("pokemon")
    val pokemon: NamedResourceApiDto = NamedResourceApiDto(),
    @SerializedName("is_hidden")
    val isHidden:Boolean = false,
):Serializable
