package com.kronos.pokedex.domian.model.type

import com.kronos.pokedex.domian.model.Name
import com.kronos.pokedex.domian.model.NamedResourceApi
import java.io.Serializable

data class TypeInfo(
    var id:Int = 0,
    var name:String = "",
    var damageRelations:DamageRelation = DamageRelation(),
    var moves:List<NamedResourceApi> = listOf(),
    var names:List<Name> = listOf(),
    var pokemon:List<NamedResourceApi> = listOf(),
):Serializable
