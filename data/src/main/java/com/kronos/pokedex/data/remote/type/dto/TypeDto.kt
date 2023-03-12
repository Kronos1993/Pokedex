package com.kronos.pokedex.data.remote.type.dto

import com.google.gson.annotations.SerializedName

data class TypeDto(
    @SerializedName("slot")
    var slot:Int = 0,
    @SerializedName("type")
    var typeInfoDto: TypeInfoDto = TypeInfoDto(),
)
