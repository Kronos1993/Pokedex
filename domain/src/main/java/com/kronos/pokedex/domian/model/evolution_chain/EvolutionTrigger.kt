package com.kronos.pokedex.domian.model.evolution_chain

import com.kronos.pokedex.domian.model.NamedResourceApi

data class EvolutionTrigger(
    val id: Int = 0,
    val name: String = "",
    val pokemonSpecies: List<NamedResourceApi> = listOf()
)
