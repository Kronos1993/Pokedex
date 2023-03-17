package com.kronos.pokedex.data.remote.stat.dto

import com.google.gson.annotations.SerializedName
import com.kronos.pokedex.data.remote.response_list.NamedResourceApiDto

data class StatDto(
    @SerializedName("base_stat")
    val baseStat:Int = 0,
    @SerializedName("stat")
    val statDto:NamedResourceApiDto = NamedResourceApiDto(),
    @SerializedName("effort")
    val effort:Int = 0,

    )
