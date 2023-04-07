package com.kronos.pokedex.data.remote.nature.dto

import com.google.gson.annotations.SerializedName
import com.kronos.pokedex.data.remote.response_list.NamedResourceApiDto

data class NatureDetailDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("decreased_stat")
    val decreasedStat: NamedResourceApiDto?,
    @SerializedName("increased_stat")
    val increasedStat: NamedResourceApiDto?,
    @SerializedName("hates_flavor")
    val hatesFlavor: NamedResourceApiDto?,
    @SerializedName("likes_flavor")
    val likesFlavor: NamedResourceApiDto?,
)
