package com.kronos.pokedex.data.remote.specie.dto

import com.google.gson.annotations.SerializedName

data class SpecieHabitatDto(
    @SerializedName("name")
    var name:String = "",
    @SerializedName("url")
    var url:String = "",
)
