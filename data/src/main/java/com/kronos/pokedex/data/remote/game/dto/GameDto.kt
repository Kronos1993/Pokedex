/*
 * Kronos Tech. Copyright (c) 2023.
 *
 */

package com.kronos.pokedex.data.remote.game.dto

import com.kronos.pokedex.data.remote.NameDto
import com.kronos.pokedex.data.remote.response_list.NamedResourceApiDto

data class GameDto(
    val id:Int = 0,
    val name:String = "",
    val names:List<NameDto> = listOf(),
    val versionGroup: NamedResourceApiDto = NamedResourceApiDto()
)
