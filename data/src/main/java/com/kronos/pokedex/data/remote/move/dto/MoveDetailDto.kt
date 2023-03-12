package com.kronos.pokedex.data.remote.move.dto

import com.google.gson.annotations.SerializedName

data class MoveDetailDto(
    @SerializedName("level_learned_at")
    var levelLearned:Int = 0,
    @SerializedName("move_learn_method")
    var moveLearnedMethodDto: MoveLearnedMethodDto = MoveLearnedMethodDto(),
)
