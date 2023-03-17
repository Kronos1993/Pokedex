package com.kronos.pokedex.domian.model.specie

import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.ResourceApi
import com.kronos.pokedex.domian.model.description.Description
import java.io.Serializable

data class SpecieInfo(
    var baseHappiness:Int = 0,
    var captureRate:Int = 0,
    var evolutionChain: ResourceApi? = ResourceApi(),
    var evolvesFrom:NamedResourceApi? = NamedResourceApi(),
    var description:List<Description> = listOf(),
    var growthRate:NamedResourceApi? = NamedResourceApi(),
    var habitat:NamedResourceApi? = NamedResourceApi(),
    var hasGenderDifferences:Boolean = false,
    var isBaby:Boolean = false,
    var isLegendary:Boolean = false,
    var isMythical:Boolean = false,
    var name:String = "",
): Serializable
