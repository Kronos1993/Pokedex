package com.kronos.pokedex.domian.model.nature

import com.kronos.pokedex.domian.model.Name
import java.io.Serializable

data class NatureDetail(
    var decreasedStat:String? = "",
    var increasedStat:String? = "",
    var hatesFlavor:String? = "",
    var likesFlavor:String? = "",
    var name:String = "",
    var names:List<Name> = listOf(),
):Serializable
