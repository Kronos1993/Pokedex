package com.kronos.pokedex.domian.model.move

import java.io.Serializable

data class MoveList(
    val move: Move,
    var moveDetails: List<MoveDetail> = listOf()
):Serializable
