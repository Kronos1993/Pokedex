package com.kronos.pokedex.data.remote.type.dto

import com.google.gson.annotations.SerializedName

data class TypeInfoDto (
    @SerializedName("name")
    val name:String = "",
    @SerializedName("url")
    val url:String = "",
)