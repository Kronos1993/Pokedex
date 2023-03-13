package com.kronos.pokedex.data.remote.move.mapper

import com.kronos.pokedex.data.remote.move.dto.MoveListDto
import com.kronos.pokedex.domian.model.move.MoveList

fun MoveListDto.toMoveList(): MoveList =
    MoveList(
        move = move.toMove(),
        moveDetails = moveDetails.map {
            it.toMoveDetail()
        },
        order = moveDetails.let {
            if (!it.isNullOrEmpty()){
                it[0].levelLearned
            }else{
                0
            }
        }
    )
