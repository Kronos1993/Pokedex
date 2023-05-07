package com.kronos.pokedex.data.remote.move.mapper

import com.kronos.pokedex.data.remote.description.mapper.toEffectEntry
import com.kronos.pokedex.data.remote.description.mapper.toFlavorText
import com.kronos.pokedex.data.remote.move.dto.MoveDetailDto
import com.kronos.pokedex.data.remote.move.dto.MoveInfoDto
import com.kronos.pokedex.data.remote.move.dto.MoveListDto
import com.kronos.pokedex.data.remote.response_list.mapper.toNamedResource
import com.kronos.pokedex.domian.model.Name
import com.kronos.pokedex.domian.model.move.MoveDetail
import com.kronos.pokedex.domian.model.move.MoveInfo
import com.kronos.pokedex.domian.model.move.MoveList

fun MoveDetailDto.toMoveDetail(): MoveDetail =
    MoveDetail(
        levelLearned = levelLearned,
        moveLearnMethod = moveLearnedMethodDto.name
    )

fun MoveInfoDto.toMoveInfo(): MoveInfo =
    MoveInfo(
        accuracy = accuracy,
        power = power,
        pp = pp,
        moveName = moveName,
        names = names.map { Name(it.name,it.language) },
        moveCategory = moveCategory.name,
        priority = priority,
        type = type.toNamedResource(),
        moveFlavorText = moveDescription.map {
            it.toFlavorText()
        },
        learnedBy = learnedBy.map {
            it.toNamedResource()
        },
        effects = effectEntries.map { it.toEffectEntry() },
        effectChance = effectChance,
    )

fun MoveListDto.toMoveList(): MoveList =
    MoveList(
        move = move.toNamedResource(),
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
