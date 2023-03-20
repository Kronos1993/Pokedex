/*
 * Copyright (c) 2022. Kronos
 * Created by Marcos Octavio Guerra Liso
 */

package com.kronos.pokedex.data.remote.pokemon.dto

import com.google.gson.annotations.SerializedName
import com.kronos.pokedex.data.remote.ability.dto.AbilityDto
import com.kronos.pokedex.data.remote.move.dto.MoveListDto
import com.kronos.pokedex.data.remote.specie.dto.SpecieInfoDto
import com.kronos.pokedex.data.remote.sprite.dto.SpriteDto
import com.kronos.pokedex.data.remote.stat.dto.StatDto
import com.kronos.pokedex.data.remote.type.dto.TypeDto
import com.kronos.pokedex.domian.model.NamedResourceApi

//models the object from pokemon api ws

data class PokemonInfoDto(
    @SerializedName("id")
    val id:Int = 0,
    @SerializedName("name")
    val name:String = "",
    @SerializedName("abilities")
    val abilities:List<AbilityDto> = listOf(),
    @SerializedName("base_experience")
    val baseExperience:Int = 0,
    @SerializedName("forms")
    val forms:List<NamedResourceApi> = listOf(),
    @SerializedName("height")
    val height:Double = 0.0,
    @SerializedName("weight")
    val weight:Double = 0.0,
    @SerializedName("types")
    val types:List<TypeDto> = listOf(),
    @SerializedName("stats")
    val stats:List<StatDto> = listOf(),
    @SerializedName("sprites")
    val sprites: SpriteDto = SpriteDto(),
    @SerializedName("moves")
    val moves:List<MoveListDto> = listOf(),
    val specieInfoDto: SpecieInfoDto = SpecieInfoDto(),

)
