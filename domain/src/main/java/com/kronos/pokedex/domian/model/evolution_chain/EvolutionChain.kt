package com.kronos.pokedex.domian.model.evolution_chain

import com.kronos.pokedex.domian.model.NamedResourceApi

data class EvolutionChain(
    var babyFriggerItem:NamedResourceApi? = NamedResourceApi(),
    var chain:ChainLink? = ChainLink(),
    var id:Int? = 0,
)
