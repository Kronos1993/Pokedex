package com.kronos.pokedex.domian.model.ability

import com.kronos.pokedex.domian.model.EffectEntry
import com.kronos.pokedex.domian.model.FlavorText
import com.kronos.pokedex.domian.model.Name
import java.io.Serializable

data class AbilityInfo(
    var id:Int = 0,
    var name:String = "",
    var names:List<Name> = listOf(),
    var pokemon:List<PokemonWithAbility> = listOf(),
    var flavorText:List<FlavorText> = listOf(),
    var effects:List<EffectEntry> = listOf(),
):Serializable
