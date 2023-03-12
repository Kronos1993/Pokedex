package com.kronos.pokedex.data.remote.evolution_chain.dto

import com.google.gson.annotations.SerializedName

data class EvolutionChainDto(
    @SerializedName("url")
    var url:String = "",
)
