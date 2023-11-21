/*
 * Kronos Tech. Copyright (c) 2023.
 *
 */

package com.kronos.pokedex.data.remote.game.mapper

import com.kronos.pokedex.data.remote.game.dto.GameIndexDto
import com.kronos.pokedex.domian.model.game.Game

fun GameIndexDto.toGame() = Game(
    name = version.name,
    url = version.url
)