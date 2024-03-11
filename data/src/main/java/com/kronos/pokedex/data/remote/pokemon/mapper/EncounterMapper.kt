/*
 * Kronos Tech. Copyright (c) 2024.
 *
 */

package com.kronos.pokedex.data.remote.pokemon.mapper

import com.kronos.pokedex.data.remote.pokemon.dto.EncounterDetailDto
import com.kronos.pokedex.data.remote.pokemon.dto.EncounterDto
import com.kronos.pokedex.data.remote.pokemon.dto.VersionDetailDto
import com.kronos.pokedex.data.remote.response_list.mapper.toNamedResource
import com.kronos.pokedex.domian.model.pokemon.Encounter
import com.kronos.pokedex.domian.model.pokemon.EncounterDetail
import com.kronos.pokedex.domian.model.pokemon.VersionDetail

fun EncounterDto.toEncounter(): Encounter =
    Encounter(
        location = location.toNamedResource(),
        versionDetails = versionDetails.map {
            it.toVersionDetail()
        }
    )

fun VersionDetailDto.toVersionDetail(): VersionDetail =
    VersionDetail(
            if (encounterDetails.isNotEmpty())
                encounterDetails[0].toEncounterDetail()
            else
                EncounterDetail(),
            maxChance,
            version.toNamedResource()
    )

fun EncounterDetailDto.toEncounterDetail(): EncounterDetail =
    EncounterDetail(
        chance,
        maxLevel,
        minLevel,
        method.toNamedResource()
    )