/*
 * Kronos Tech. Copyright (c) 2023.
 *
 */

package com.kronos.pokedex.data.remote.game.dto

import com.google.gson.annotations.SerializedName
import com.kronos.pokedex.data.remote.response_list.NamedResourceApiDto

data class GameIndexDto(
    @SerializedName("game_index")
    val gameIndex:Int = -1,
    val version:NamedResourceApiDto = NamedResourceApiDto()
)
