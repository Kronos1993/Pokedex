package com.kronos.pokedex.domian.model.stat

import java.io.Serializable

data class Stat(
    val baseStat:Int = 0,
    val statName:String = "",
    val statEffort:Int = 0,
):Serializable
