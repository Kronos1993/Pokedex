/*
 * Kronos Tech. Copyright (c) 2024.
 *
 */

package com.kronos.pokedex.data.remote.pokemon.dto

import com.google.gson.annotations.SerializedName
import com.kronos.pokedex.data.remote.response_list.NamedResourceApiDto
import java.io.Serializable

data class VersionDetailDto(
    @SerializedName("encounter_details")
    var encounterDetails: List<EncounterDetailDto> = listOf(),
    @SerializedName("max_chance")
    var maxChance:Int = 0,
    @SerializedName("version")
    var version: NamedResourceApiDto = NamedResourceApiDto()
): Serializable
