package com.kronos.pokedex.data.remote.type.dto

import com.google.gson.annotations.SerializedName
import com.kronos.pokedex.data.remote.NameDto
import com.kronos.pokedex.data.remote.response_list.NamedResourceApiDto
import java.io.Serializable

data class TypeInfoDto(
    @SerializedName("id")
    var id:Int = 0,
    @SerializedName("name")
    var name:String = "",
    @SerializedName("damage_relations")
    var damageRelations:DamageRelationDto = DamageRelationDto(),
    @SerializedName("moves")
    var moves:List<NamedResourceApiDto> = listOf(),
    @SerializedName("names")
    var names:List<NameDto> = listOf(),
    @SerializedName("pokemon")
    var pokemon:List<NamedResourceApiDto> = listOf(),
):Serializable
