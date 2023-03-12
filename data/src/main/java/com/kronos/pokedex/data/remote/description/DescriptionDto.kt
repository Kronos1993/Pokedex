package com.kronos.pokedex.data.remote.description

import com.google.gson.annotations.SerializedName

data class DescriptionDto(
    @SerializedName("flavor_text")
    var description:String = "",
    @SerializedName("language")
    var language:DescriptionLanguageDto = DescriptionLanguageDto(),

    )