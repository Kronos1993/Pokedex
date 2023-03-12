package com.kronos.pokedex.data.remote.move.dto

import com.google.gson.annotations.SerializedName

data class MoveLearnedMethodDto(
    @SerializedName("name")
    var name:String = "",
    @SerializedName("url")
    var url:String = "",
)
