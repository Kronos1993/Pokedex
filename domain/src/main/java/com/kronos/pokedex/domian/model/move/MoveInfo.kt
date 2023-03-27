package com.kronos.pokedex.domian.model.move

import com.kronos.pokedex.domian.model.EffectEntry
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.flavor_text.FlavorText
import com.kronos.pokedex.domian.model.type.Type
import java.io.Serializable

data class MoveInfo(
    var accuracy: Int = 0,
    var moveCategory: String = "",
    var moveFlavorText: List<FlavorText> = listOf(),
    var learnedBy: List<NamedResourceApi> = listOf(),
    var moveName: String = "",
    var power: Int = 0,
    var pp: Int = 0,
    var priority: Int = 0,
    var type:NamedResourceApi = NamedResourceApi(),
    var effects:List<EffectEntry> = listOf(),
): Serializable
