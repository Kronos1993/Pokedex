/*
 * Kronos Tech. Copyright (c) 2024.
 *
 */

package com.kronos.pokedex.data.remote.pokemon.dto

import com.google.gson.annotations.SerializedName
import com.kronos.pokedex.data.remote.response_list.NamedResourceApiDto
import java.io.Serializable

data class EncounterDto (
    @SerializedName("location_area")
    var location:NamedResourceApiDto = NamedResourceApiDto(),
    @SerializedName("version_details")
    var versionDetails:List<VersionDetailDto> = listOf(),
): Serializable