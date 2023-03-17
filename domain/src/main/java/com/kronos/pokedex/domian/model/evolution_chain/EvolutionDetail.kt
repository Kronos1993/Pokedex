package com.kronos.pokedex.domian.model.evolution_chain

import com.kronos.pokedex.domian.model.NamedResourceApi

data class EvolutionDetail(
    val trigger: NamedResourceApi? = NamedResourceApi(),
    val item: NamedResourceApi? = NamedResourceApi(),
    val gender: Int? = 0,
    val heldItem: NamedResourceApi? = NamedResourceApi(),
    val knownMove: NamedResourceApi? = NamedResourceApi(),
    val knownMoveType: NamedResourceApi? = NamedResourceApi(),
    val location: NamedResourceApi? = NamedResourceApi(),
    val minLevel: Int? = 0,
    val minHappiness: Int? = 0,
    val minBeauty: Int? = 0,
    val minAffection: Int? = 0,
    val partySpecies: NamedResourceApi? = NamedResourceApi(),
    val partyType: NamedResourceApi? = NamedResourceApi(),
    val relativePhysicalStats: Int? = 0,
    val timeOfDay: String? = "",
    val tradeSpecies: NamedResourceApi? = NamedResourceApi(),
    val needsOverworldRain: Boolean = false,
    val turnUpsideDown: Boolean = false
)
