package com.kronos.pokedex.domian.model.ability

import com.kronos.pokedex.domian.model.EffectEntry
import com.kronos.pokedex.domian.model.flavor_text.FlavorText
import java.io.Serializable

data class AbilityInfo(
    var id:Int = 0,
    var name:String = "",
    var pokemon:List<PokemonWithAbility> = listOf(),
    var flavorText:List<FlavorText> = listOf(),
    var effects:List<EffectEntry> = listOf(),
):Serializable
