package com.kronos.pokedex.data.remote.item.dto

import com.google.gson.annotations.SerializedName
import com.kronos.pokedex.data.remote.NameDto
import com.kronos.pokedex.data.remote.response_list.NamedResourceApiDto
import java.io.Serializable

data class ItemCategoryDto(
    @SerializedName("id")
    var id:Int = 0,
    @SerializedName("items")
    var items:List<NamedResourceApiDto> = listOf(),
    @SerializedName("name")
    var name: String = "",
    @SerializedName("names")
    val names: List<NameDto>,
    @SerializedName("pocket")
    var pocket: NamedResourceApiDto = NamedResourceApiDto()
):Serializable