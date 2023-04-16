package com.kronos.pokedex.domian.model.evolution_chain

import com.kronos.pokedex.domian.model.NamedResourceApi
import java.io.Serializable

data class ChainLink (
    var evolutionDetails:List<EvolutionDetail> = listOf(),
    var evolvesTo:List<ChainLink> = listOf(),
    var isBaby:Boolean = false,
    var species:NamedResourceApi= NamedResourceApi()
):Serializable