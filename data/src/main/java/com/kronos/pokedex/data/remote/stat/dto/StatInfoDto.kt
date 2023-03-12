package com.kronos.pokedex.data.remote.stat.dto

import com.google.gson.annotations.SerializedName

data class StatInfoDto (
    @SerializedName("name")
    val name:String = "",
    @SerializedName("url")
    val url:String = "",
)