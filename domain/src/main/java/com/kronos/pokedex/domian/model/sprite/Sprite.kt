package com.kronos.pokedex.domian.model.sprite

import java.io.Serializable

data class Sprite(
    val defaultImg:String = "",
    val backDefault:String = "",
    val backFemale:String = "",
    val backShiny:String = "",
    val backShinyFemale:String = "",
    val frontDefault:String = "",
    val frontFemale:String = "",
    val frontShiny:String = "",
    val frontShinyFemale:String = "",
    val frontHome:String = "",
    val frontHomeShiny:String = "",
): Serializable
