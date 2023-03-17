package com.kronos.pokedex.data.remote.move.dto

import com.google.gson.annotations.SerializedName
import com.kronos.pokedex.data.remote.response_list.NamedResourceApiDto

data class MoveDetailDto(
    @SerializedName("level_learned_at")
    var levelLearned:Int = 0,
    @SerializedName("move_learn_method")
    var moveLearnedMethodDto: NamedResourceApiDto = NamedResourceApiDto(),
)
