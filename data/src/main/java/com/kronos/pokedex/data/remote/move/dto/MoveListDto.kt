package com.kronos.pokedex.data.remote.move.dto

import com.google.gson.annotations.SerializedName

data class MoveListDto(
    @SerializedName("move")
    val move: MoveDto,
    @SerializedName("version_group_details")
    var moveDetails: List<MoveDetailDto> = listOf()

)