package com.kronos.pokedex.data.remote.description

import com.google.gson.annotations.SerializedName

data class DescriptionLanguageDto(
    @SerializedName("name")
    var name:String = "",
    @SerializedName("url")
    var url:String = "",
)