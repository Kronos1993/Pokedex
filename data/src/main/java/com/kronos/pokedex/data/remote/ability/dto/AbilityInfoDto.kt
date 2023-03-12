package com.kronos.pokedex.data.remote.ability.dto

import com.google.gson.annotations.SerializedName

data class AbilityInfoDto(
    @SerializedName("name")
    var name:String = "",
    @SerializedName("url")
    var url:String = "",
)
