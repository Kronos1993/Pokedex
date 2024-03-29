package com.kronos.pokedex.data.remote.specie.dto

import com.google.gson.annotations.SerializedName
import com.kronos.pokedex.data.remote.NameDto
import com.kronos.pokedex.data.remote.description.FlavorTextEntryDto
import com.kronos.pokedex.data.remote.response_list.NamedResourceApiDto
import com.kronos.pokedex.domian.model.ResourceApi

data class SpecieInfoDto(
    @SerializedName("base_happiness")
    var baseHappiness:Int = 0,
    @SerializedName("capture_rate")
    var captureRate:Int = 0,
    @SerializedName("evolution_chain")
    var evolutionChain: ResourceApi = ResourceApi(),
    @SerializedName("evolves_from_species")
    var evolvesFrom:NamedResourceApiDto = NamedResourceApiDto(),
    @SerializedName("flavor_text_entries")
    var description:List<FlavorTextEntryDto> = listOf(),
    @SerializedName("growth_rate")
    var growthRate:NamedResourceApiDto = NamedResourceApiDto(),
    @SerializedName("gender_rate")
    var genderRate:Int = -1,
    @SerializedName("hatch_counter")
    var hatchCounter:Int = -1,
    @SerializedName("habitat")
    var habitat:NamedResourceApiDto = NamedResourceApiDto(),
    @SerializedName("has_gender_differences")
    var hasGenderDifferences:Boolean = false,
    @SerializedName("is_baby")
    var isBaby:Boolean = false,
    @SerializedName("is_legendary")
    var isLegendary:Boolean = false,
    @SerializedName("is_mythical")
    var isMythical:Boolean = false,
    @SerializedName("name")
    var name:String = "",
    @SerializedName("names")
    val names: List<NameDto> = listOf(),
    @SerializedName("varieties")
    var varieties:List<SpecieVarietiesDto> = listOf(),
    @SerializedName("egg_groups")
    var eggGroups:List<NamedResourceApiDto> = listOf(),
    @SerializedName("genera")
    var genera:List<PokemonGeneraDto> = listOf(),
)
