package com.kronos.pokedex.domian.model.type

import com.kronos.pokedex.domian.model.NamedResourceApi
import java.io.Serializable

data class DamageRelation(
    var doubleDamageFrom:List<NamedResourceApi> = listOf(),
    var doubleDamageTo:List<NamedResourceApi> = listOf(),
    var halfDamageFrom:List<NamedResourceApi> = listOf(),
    var halfDamageTo:List<NamedResourceApi> = listOf(),
    var noDamageFrom:List<NamedResourceApi> = listOf(),
    var noDamageTo:List<NamedResourceApi> = listOf(),
):Serializable
