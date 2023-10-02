package com.kronos.pokedex.domian.model.pokemon

import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.ability.Ability
import com.kronos.pokedex.domian.model.move.MoveList
import com.kronos.pokedex.domian.model.specie.SpecieInfo
import com.kronos.pokedex.domian.model.sprite.Sprite
import com.kronos.pokedex.domian.model.stat.Stat
import com.kronos.pokedex.domian.model.type.Type
import java.io.Serializable

data class PokemonInfo(
    val id:Int = 0,
    val name:String = "",
    val abilities:List<Ability> = listOf(),
    val baseExperience:Int = 0,
    val height:Double = 0.0,
    val weight:Double = 0.0,
    val types:List<Type> = listOf(),
    val stats:List<Stat> = listOf(),
    val sprites:Sprite = Sprite(),
    val moves:List<MoveList> = listOf(),
    var specieInfo: SpecieInfo = SpecieInfo(),
    var specie: NamedResourceApi = NamedResourceApi(),
): Serializable
