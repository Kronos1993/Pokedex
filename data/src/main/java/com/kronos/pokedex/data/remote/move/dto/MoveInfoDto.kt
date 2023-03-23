package com.kronos.pokedex.data.remote.move.dto

import com.google.gson.annotations.SerializedName
import com.kronos.pokedex.data.remote.description.FlavorTextDto
import com.kronos.pokedex.data.remote.response_list.NamedResourceApiDto
import com.kronos.pokedex.data.remote.type.dto.TypeDto

data class MoveInfoDto(
    @SerializedName("accuracy")
    var accuracy: Int = 0,
    @SerializedName("damage_class")
    var moveCategory: NamedResourceApiDto = NamedResourceApiDto(),
    @SerializedName("flavor_text_entries")
    var moveDescription: List<FlavorTextDto> = listOf(),
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
    var type: TypeDto = TypeDto()
)
