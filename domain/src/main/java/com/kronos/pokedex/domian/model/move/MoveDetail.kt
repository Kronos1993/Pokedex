package com.kronos.pokedex.domian.model.move

import java.io.Serializable

data class MoveDetail(
    var levelLearned:Int = 0,
    var moveLearnMethod:String = "",
): Serializable
