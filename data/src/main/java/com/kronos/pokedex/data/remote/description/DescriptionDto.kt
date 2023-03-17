package com.kronos.pokedex.data.remote.description

import com.google.gson.annotations.SerializedName
import com.kronos.pokedex.data.remote.response_list.NamedResourceApiDto

data class DescriptionDto(
    @SerializedName("flavor_text")
    var description:String = "",
    @SerializedName("language")
    var language:NamedResourceApiDto = NamedResourceApiDto(),
    )