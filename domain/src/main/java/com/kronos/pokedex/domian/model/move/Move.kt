package com.kronos.pokedex.domian.model.move

import java.io.Serializable

data class Move(
    var name:String = "",
    var url:String = "",
    var moveDetail:MoveDetail = MoveDetail()
): Serializable
