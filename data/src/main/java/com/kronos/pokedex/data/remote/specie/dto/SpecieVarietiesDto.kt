package com.kronos.pokedex.data.remote.specie.dto

import com.google.gson.annotations.SerializedName
import com.kronos.pokedex.data.remote.response_list.NamedResourceApiDto

data class SpecieVarietiesDto(
    @SerializedName("is_default")
    var is_default: Boolean = false,
    @SerializedName("pokemon")
    var pokemon: NamedResourceApiDto = NamedResourceApiDto(),
)
