package com.kronos.pokedex.domian.model.egg_group

import com.kronos.pokedex.domian.model.Name
import com.kronos.pokedex.domian.model.NamedResourceApi
import java.io.Serializable

data class EggGroupInfo(
    var id:Int = 0,
    var name:String = "",
    var names:List<Name> = listOf(),
    var pokemonSpecies:List<NamedResourceApi> = listOf(),
):Serializable
