package com.kronos.pokedex.data.remote.move.mapper

import com.kronos.pokedex.data.remote.move.dto.MoveDto
import com.kronos.pokedex.domian.model.move.Move

fun MoveDto.toMove(): Move =
    Move(
        name = name,
        url = url
    )
