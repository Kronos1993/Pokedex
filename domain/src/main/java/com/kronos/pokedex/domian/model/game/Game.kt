/*
 * Kronos Tech. Copyright (c) 2023.
 *
 */

package com.kronos.pokedex.domian.model.game

import com.kronos.pokedex.domian.model.NamedResourceApi
import java.util.jar.Attributes.Name

data class Game(
    val id:Int = 0,
    val name:String = "",
    val url:String = "",
    val names:List<Name> = listOf(),
    val versionGroup:NamedResourceApi = NamedResourceApi()
)
