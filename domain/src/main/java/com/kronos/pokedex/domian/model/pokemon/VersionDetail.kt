/*
 * Kronos Tech. Copyright (c) 2024.
 *
 */

package com.kronos.pokedex.domian.model.pokemon

import com.kronos.pokedex.domian.model.NamedResourceApi
import java.io.Serializable

data class VersionDetail(
    var encounterDetail: EncounterDetail = EncounterDetail(),
    var maxChance:Int = 100,
    var version: NamedResourceApi = NamedResourceApi()
): Serializable
