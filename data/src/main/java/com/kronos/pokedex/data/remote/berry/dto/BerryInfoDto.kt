package com.kronos.pokedex.data.remote.berry.dto

import com.google.gson.annotations.SerializedName
import com.kronos.pokedex.data.remote.NameDto
import com.kronos.pokedex.data.remote.item.dto.ItemInfoDto
import com.kronos.pokedex.data.remote.response_list.NamedResourceApiDto
import com.kronos.pokedex.domian.model.NamedResourceApi
import java.io.Serializable

data class BerryInfoDto(
    @SerializedName("firmness")
    var firmness:NamedResourceApiDto = NamedResourceApiDto(),
    @SerializedName("flavors")
    var flavors:List<BerryFlavorDto> = listOf(),
    @SerializedName("growth_time")
    var growthTime:Int = 0,
    @SerializedName("id")
    var id:Int = 0,
    @SerializedName("item")
    var itemResource:NamedResourceApiDto = NamedResourceApiDto(),
    @SerializedName("max_harvest")
    var maxHarvest:Int = 0,
    @SerializedName("name")
    var name:String = "",
    @SerializedName("names")
    val names: List<NameDto>,
    @SerializedName("natural_gift_power")
    var naturalGiftPower:Int = 0,
    @SerializedName("natural_gift_type")
    var naturalGiftType:NamedResourceApiDto = NamedResourceApiDto(),
    @SerializedName("size")
    var size:Int = 0,
    @SerializedName("smoothness")
    var smoothness:Int = 0,
    @SerializedName("soil_dryness")
    var soilDryness:Int = 0
):Serializable
