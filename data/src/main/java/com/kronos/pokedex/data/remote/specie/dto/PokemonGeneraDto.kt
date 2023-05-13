package com.kronos.pokedex.data.remote.specie.dto

import com.google.gson.annotations.SerializedName
import com.kronos.pokedex.data.remote.response_list.NamedResourceApiDto

data class PokemonGeneraDto(
    @SerializedName("genus")
    var genus:String = "",
    @SerializedName("language")
    var language: NamedResourceApiDto
)
