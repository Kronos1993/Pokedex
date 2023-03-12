package com.kronos.pokedex.data.remote.ability.dto

import com.google.gson.annotations.SerializedName

data class AbilityDto(
    @SerializedName("ability")
    val ability: AbilityInfoDto = AbilityInfoDto(),
    @SerializedName("is_hidden")
    val isHidden:Boolean = true,
)
