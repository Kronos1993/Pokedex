package com.kronos.pokedex.domian.model.move

import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.description.Description
import com.kronos.pokedex.domian.model.type.Type
import java.io.Serializable

data class MoveInfo(
    var accuracy: Int = 0,
    var moveCategory: String = "",
    var moveDescription: List<Description> = listOf(),
    var learnedBy: List<NamedResourceApi> = listOf(),
    var moveName: String = "",
    var power: Int = 0,
    var pp: Int = 0,
    var priority: Int = 0,
    var type:Type = Type()
): Serializable
