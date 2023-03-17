package com.kronos.pokedex.data.remote.move.dto

import com.google.gson.annotations.SerializedName
import com.kronos.pokedex.data.remote.response_list.NamedResourceApiDto

data class MoveListDto(
    @SerializedName("move")
    val move: NamedResourceApiDto = NamedResourceApiDto(),
    @SerializedName("version_group_details")
    var moveDetails: List<MoveDetailDto> = listOf()

)