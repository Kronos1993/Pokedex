package com.kronos.pokedex.data.remote.evolution_chain.dto

import com.google.gson.annotations.SerializedName
import com.kronos.pokedex.data.remote.response_list.NamedResourceApiDto

data class ChainLinkDto (
    @SerializedName("evolution_details")
    var evolutionDetails:List<EvolutionDetailDto>? = listOf(),
    @SerializedName("evolves_to")
    var evolvesTo:List<ChainLinkDto>? = listOf(),
    @SerializedName("is_baby")
    var isBaby:Boolean = false,
    @SerializedName("species")
    var species:NamedResourceApiDto? = NamedResourceApiDto()
)