package com.kronos.pokedex.domian.model.move

import com.kronos.pokedex.domian.model.NamedResourceApi
import java.io.Serializable

data class MoveList(
    val move: NamedResourceApi,
    var moveDetails: List<MoveDetail> = listOf(),
    var order : Int = 0
):Serializable
