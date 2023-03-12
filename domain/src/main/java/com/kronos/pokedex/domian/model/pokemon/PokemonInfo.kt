package com.kronos.pokedex.domian.model.pokemon

import com.kronos.pokedex.domian.model.ability.Ability
import com.kronos.pokedex.domian.model.form.Form
import com.kronos.pokedex.domian.model.move.Move
import com.kronos.pokedex.domian.model.move.MoveList
import com.kronos.pokedex.domian.model.specie.Specie
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
    val forms:List<Form> = listOf(),
    val height:Int = 0,
    val weight:Int = 0,
    val types:List<Type> = listOf(),
    val stats:List<Stat> = listOf(),
    val sprites:Sprite = Sprite(),
    val moves:List<MoveList> = listOf(),
    var specie: SpecieInfo = SpecieInfo(),
): Serializable
