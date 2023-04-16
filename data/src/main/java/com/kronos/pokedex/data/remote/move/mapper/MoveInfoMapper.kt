package com.kronos.pokedex.data.remote.move.mapper

import com.kronos.pokedex.data.remote.description.mapper.toEffectEntry
import com.kronos.pokedex.data.remote.description.mapper.toFlavorText
import com.kronos.pokedex.data.remote.move.dto.MoveInfoDto
import com.kronos.pokedex.data.remote.response_list.mapper.toNamedResource
import com.kronos.pokedex.data.remote.type.mapper.toType
import com.kronos.pokedex.domian.model.move.MoveInfo

fun MoveInfoDto.toMoveInfo(): MoveInfo =
    MoveInfo(
        accuracy = accuracy,
        power = power,
        pp = pp,
        moveName = moveName,
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
