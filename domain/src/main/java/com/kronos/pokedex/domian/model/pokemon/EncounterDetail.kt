/*
 * Kronos Tech. Copyright (c) 2024.
 *
 */

package com.kronos.pokedex.domian.model.pokemon

import com.kronos.pokedex.domian.model.NamedResourceApi
import java.io.Serializable

data class EncounterDetail(
    var chance: Int = 0,
    var maxLevel:Int = 0,
    var minLevel:Int = 0,
    var method:NamedResourceApi = NamedResourceApi()
): Serializable
