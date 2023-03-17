package com.kronos.pokedex.data.remote.evolution_chain.dto

import com.google.gson.annotations.SerializedName
import com.kronos.pokedex.data.remote.response_list.NamedResourceApiDto

data class EvolutionDetailDto(
    @SerializedName("trigger")
    val trigger: NamedResourceApiDto? = NamedResourceApiDto(),
    @SerializedName("item")
    val item: NamedResourceApiDto? = NamedResourceApiDto(),
    @SerializedName("gender")
    val gender: Int? = 0,
    @SerializedName("held_item")
    val heldItem: NamedResourceApiDto? = NamedResourceApiDto(),
    @SerializedName("known_move")
    val knownMove: NamedResourceApiDto? = NamedResourceApiDto(),
    @SerializedName("known_move_type")
    val knownMoveType: NamedResourceApiDto? = NamedResourceApiDto(),
    @SerializedName("location")
    val location: NamedResourceApiDto? = NamedResourceApiDto(),
    @SerializedName("min_level")
    val minLevel: Int? = 0,
    @SerializedName("min_happiness")
    val minHappiness: Int? = 0,
    @SerializedName("min_beauty")
    val minBeauty: Int? = 0,
    @SerializedName("min_affection")
    val minAffection: Int? = 0,
    @SerializedName("party_species")
    val partySpecies: NamedResourceApiDto? = NamedResourceApiDto(),
    @SerializedName("party_type")
    val partyType: NamedResourceApiDto? = NamedResourceApiDto(),
    @SerializedName("relative_physical_stats")
    val relativePhysicalStats: Int? = 0,
    @SerializedName("time_of_day")
    val timeOfDay: String? = "",
    @SerializedName("trade_species")
    val tradeSpecies: NamedResourceApiDto? = NamedResourceApiDto(),
    @SerializedName("needs_overworld_rain")
    val needsOverworldRain: Boolean = false,
    @SerializedName("turn_upside_down")
    val turnUpsideDown: Boolean = false
)
