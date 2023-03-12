package com.kronos.pokedex.data.remote.move.mapper

import com.kronos.pokedex.data.remote.description.mapper.toDescription
import com.kronos.pokedex.data.remote.move.dto.MoveInfoDto
import com.kronos.pokedex.data.remote.pokemon.mapper.toPokemonList
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
        type = type.toType(),
        moveDescription = moveDescription.map {
            it.toDescription()
        },
        learnedBy = learnedBy.map {
            it.toPokemonList()
        }
    )
