package com.kronos.pokedex.data.remote.move.mapper

import com.kronos.pokedex.data.remote.move.dto.MoveDetailDto
import com.kronos.pokedex.data.remote.move.dto.MoveListDto
import com.kronos.pokedex.domian.model.move.MoveDetail
import com.kronos.pokedex.domian.model.move.MoveList

fun MoveDetailDto.toMoveDetail(): MoveDetail =
    MoveDetail(
        levelLearned = levelLearned,
        moveLearnMethod = moveLearnedMethodDto.name
    )
