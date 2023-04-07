package com.kronos.pokedex.data.remote.item.dto

import com.google.gson.annotations.SerializedName
import com.kronos.pokedex.data.remote.response_list.NamedResourceApiDto
import java.io.Serializable

data class ItemHeldByPokemonDto(
    @SerializedName("pokemon")
    var pokemon:NamedResourceApiDto = NamedResourceApiDto(),
):Serializable
