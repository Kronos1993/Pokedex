package com.kronos.pokedex.data.remote.berry.dto

import com.google.gson.annotations.SerializedName
import com.kronos.pokedex.data.remote.response_list.NamedResourceApiDto
import com.kronos.pokedex.domian.model.NamedResourceApi
import java.io.Serializable

data class BerryFlavorDto(
    @SerializedName("flavor")
    var flavor: NamedResourceApiDto = NamedResourceApiDto(),
    @SerializedName("potency")
    var potency:Int = 0,
): Serializable