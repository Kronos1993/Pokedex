package com.kronos.pokedex.data.remote.type.dto

import com.google.gson.annotations.SerializedName
import com.kronos.pokedex.data.remote.response_list.NamedResourceApiDto

data class TypeDto(
    @SerializedName("slot")
    var slot:Int = 0,
    @SerializedName("type")
    var typeInfoDto: NamedResourceApiDto = NamedResourceApiDto(),
)
