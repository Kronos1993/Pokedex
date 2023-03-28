package com.kronos.pokedex.data.remote.move.dto

import com.google.gson.annotations.SerializedName
import com.kronos.pokedex.data.remote.description.EffectEntryDto
import com.kronos.pokedex.data.remote.description.FlavorTextDto
import com.kronos.pokedex.data.remote.response_list.NamedResourceApiDto

data class MoveInfoDto(
    @SerializedName("accuracy")
    var accuracy: Int = 0,
    @SerializedName("damage_class")
    var moveCategory: NamedResourceApiDto = NamedResourceApiDto(),
    @SerializedName("flavor_text_entries")
    var moveDescription: List<FlavorTextDto> = listOf(),
    @SerializedName("effect_entries")
    val effectEntries: List<EffectEntryDto>,
    @SerializedName("learned_by_pokemon")
    var learnedBy: List<NamedResourceApiDto> = listOf(),
    @SerializedName("name")
    var moveName: String = "",
    @SerializedName("power")
    var power: Int = 0,
    @SerializedName("pp")
    var pp: Int = 0,
    @SerializedName("priority")
    var priority: Int = 0,
    @SerializedName("type")
    var type: NamedResourceApiDto = NamedResourceApiDto(),
    @SerializedName("effect_chance")
    var effectChance: Int? = 0,
)
