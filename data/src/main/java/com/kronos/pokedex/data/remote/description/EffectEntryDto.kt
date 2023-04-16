package com.kronos.pokedex.data.remote.description

import com.google.gson.annotations.SerializedName
import com.kronos.pokedex.data.remote.response_list.NamedResourceApiDto
import java.io.Serializable

data class EffectEntryDto(
    @SerializedName("effect")
    var effect:String = "",
    @SerializedName("short_effect")
    var shortEffect:String = "",
    @SerializedName("language")
    val language: NamedResourceApiDto,
):Serializable