package com.kronos.pokedex.data.remote.ability.dto

import com.google.gson.annotations.SerializedName
import com.kronos.pokedex.data.remote.response_list.NamedResourceApiDto

data class AbilityDto(
    @SerializedName("ability")
    val ability: NamedResourceApiDto = NamedResourceApiDto(),
    @SerializedName("is_hidden")
    val isHidden:Boolean = true,
)
