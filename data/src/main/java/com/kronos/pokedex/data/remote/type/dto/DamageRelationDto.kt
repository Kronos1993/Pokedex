package com.kronos.pokedex.data.remote.type.dto

import com.google.gson.annotations.SerializedName
import com.kronos.pokedex.data.remote.response_list.NamedResourceApiDto
import com.kronos.pokedex.domian.model.NamedResourceApi
import java.io.Serializable

data class DamageRelationDto(
    @SerializedName("double_damage_from")
    var doubleDamageFrom:List<NamedResourceApiDto> = listOf(),
    @SerializedName("double_damage_to")
    var doubleDamageTo:List<NamedResourceApiDto> = listOf(),
    @SerializedName("half_damage_from")
    var halfDamageFrom:List<NamedResourceApiDto> = listOf(),
    @SerializedName("half_damage_to")
    var halfDamageTo:List<NamedResourceApiDto> = listOf(),
    @SerializedName("no_damage_from")
    var noDamageFrom:List<NamedResourceApiDto> = listOf(),
    @SerializedName("no_damage_to")
    var noDamageTo:List<NamedResourceApiDto> = listOf(),
):Serializable
