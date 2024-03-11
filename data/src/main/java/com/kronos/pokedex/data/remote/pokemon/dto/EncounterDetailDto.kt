/*
 * Kronos Tech. Copyright (c) 2024.
 *
 */

package com.kronos.pokedex.data.remote.pokemon.dto

import com.google.gson.annotations.SerializedName
import com.kronos.pokedex.data.remote.response_list.NamedResourceApiDto
import java.io.Serializable

data class EncounterDetailDto(
    @SerializedName("chance")
    var chance: Int = 0,
    @SerializedName("max_level")
    var maxLevel:Int = 0,
    @SerializedName("min_level")
    var minLevel:Int = 0,
    @SerializedName("method")
    var method:NamedResourceApiDto = NamedResourceApiDto()
): Serializable
