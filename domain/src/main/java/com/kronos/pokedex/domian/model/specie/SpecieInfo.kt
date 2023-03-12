package com.kronos.pokedex.domian.model.specie

import com.kronos.pokedex.domian.model.description.Description
import com.kronos.pokedex.domian.model.evolution_chain.EvolutionChain
import java.io.Serializable

data class SpecieInfo(
    var baseHappiness:Int = 0,
    var captureRate:Int = 0,
    var evolutionChain: EvolutionChain? = EvolutionChain(),
    var evolvesFrom:Specie? = Specie(),
    var description:List<Description> = listOf(),
    var growthRate:SpecieGrowthRate? = SpecieGrowthRate(),
    var habitat:SpecieHabitat? = SpecieHabitat(),
    var hasGenderDifferences:Boolean = false,
    var isBaby:Boolean = false,
    var isLegendary:Boolean = false,
    var isMythical:Boolean = false,
    var name:String = "",
): Serializable
