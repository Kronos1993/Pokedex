package com.kronos.pokedex.data.remote

import com.kronos.pokedex.domian.model.NamedResourceApi

data class Language(
    val id: Int,
    val name: String,
    val official: Boolean,
    val iso639: String,
    val iso3166: String,
    val names: List<Name>
)

data class Description(
    val description: String,
    val language: NamedResourceApi
)

data class Effect(
    val effect: String,
    val language: NamedResourceApi
)

data class Encounter(
    val minLevel: Int,
    val maxLevel: Int,
    val conditionValues: List<NamedResourceApi>,
    val chance: Int,
    val method: NamedResourceApi
)

data class FlavorText(
    val flavorText: String,
    val language: NamedResourceApi
)

data class GenerationGameIndex(
    val gameIndex: Int,
    val generation: NamedResourceApi
)

data class MachineVersionDetail(
    val machine: NamedResourceApi,
    val versionGroup: NamedResourceApi
)

data class Name(
    val name: String,
    val language: NamedResourceApi
)

data class VerboseEffect(
    val effect: String,
    val shortEffect: String,
    val language: NamedResourceApi
)

data class VersionEncounterDetail(
    val version: NamedResourceApi,
    val maxChance: Int,
    val encounterDetails: List<Encounter>
)

data class VersionGameIndex(
    val gameIndex: Int,
    val version: NamedResourceApi
)

data class VersionGroupFlavorText(
    val text: String,
    val language: NamedResourceApi,
    val versionGroup: NamedResourceApi
)