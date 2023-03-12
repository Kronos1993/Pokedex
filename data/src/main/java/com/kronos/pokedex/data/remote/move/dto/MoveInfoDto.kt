package com.kronos.pokedex.data.remote.move.dto

import com.google.gson.annotations.SerializedName
import com.kronos.pokedex.data.remote.description.DescriptionDto
import com.kronos.pokedex.data.remote.pokemon.dto.PokemonListDto
import com.kronos.pokedex.data.remote.type.dto.TypeDto
import com.kronos.pokedex.domian.model.type.Type

data class MoveInfoDto(
    @SerializedName("accuracy")
    var accuracy: Int = 0,
    @SerializedName("damage_class")
    var moveCategory: MoveClassDto = MoveClassDto(),
    @SerializedName("flavor_text_entries")
    var moveDescription: List<DescriptionDto> = listOf(),
    @SerializedName("learned_by_pokemon")
    var learnedBy: List<PokemonListDto> = listOf(),
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
