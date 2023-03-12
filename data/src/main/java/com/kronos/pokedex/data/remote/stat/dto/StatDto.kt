package com.kronos.pokedex.data.remote.stat.dto

import com.google.gson.annotations.SerializedName

data class StatDto(
    @SerializedName("base_stat")
    val baseStat:Int = 0,
    @SerializedName("stat")
    val statDto:StatInfoDto = StatInfoDto(),
    @SerializedName("effort")
    val effort:Int = 0,

)
