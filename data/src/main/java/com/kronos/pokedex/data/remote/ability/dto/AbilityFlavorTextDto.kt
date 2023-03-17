package com.kronos.pokedex.data.remote.ability.dto

import com.google.gson.annotations.SerializedName
import com.kronos.pokedex.data.remote.response_list.NamedResourceApiDto

data class AbilityFlavorTextDto(
    @SerializedName("flavor_text")
    val flavorText: String,
    @SerializedName("language")
    val language: NamedResourceApiDto,
    @SerializedName("version_group")
    val versionGroup: NamedResourceApiDto
)
