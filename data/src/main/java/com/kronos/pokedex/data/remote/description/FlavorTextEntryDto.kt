package com.kronos.pokedex.data.remote.description

import com.google.gson.annotations.SerializedName
import com.kronos.pokedex.data.remote.response_list.NamedResourceApiDto

data class FlavorTextEntryDto(
    @SerializedName("flavor_text")
    val flavorText: String,
    @SerializedName("language")
    val language: NamedResourceApiDto,
    @SerializedName("version_group")
    val versionGroup: NamedResourceApiDto
    )