package com.kronos.pokedex.data.remote.evolution_chain.dto

import com.google.gson.annotations.SerializedName
import com.kronos.pokedex.data.remote.response_list.NamedResourceApiDto

data class EvolutionChainDto(
    @SerializedName("baby_trigger_item")
    var babyFriggerItem:NamedResourceApiDto? = NamedResourceApiDto(),
    @SerializedName("chain")
    var chain:ChainLinkDto? = ChainLinkDto(),
    @SerializedName("id")
    var id:Int = 0,
)
