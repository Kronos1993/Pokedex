/*
 * Kronos Tech. Copyright (c) 2024.
 *
 */

package com.kronos.pokedex.domian.model.pokemon

import com.kronos.pokedex.domian.model.NamedResourceApi
import java.io.Serializable

data class Encounter (
    var location:NamedResourceApi = NamedResourceApi(),
    var versionDetails:List<VersionDetail> = listOf(),
): Serializable