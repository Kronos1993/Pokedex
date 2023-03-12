/*
 * Copyright (c) 2022. Kronos
 * Created by Marcos Octavio Guerra Liso
 */

package com.kronos.pokedex.domian.model

import java.io.Serializable

data class ResponseList<Any>(
    var count:Int = 0,
    var next:String = "",
    var results: List<Any> = listOf(),
): Serializable
