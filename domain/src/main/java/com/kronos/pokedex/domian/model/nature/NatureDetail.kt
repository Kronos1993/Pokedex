package com.kronos.pokedex.domian.model.nature

import com.kronos.pokedex.domian.model.Name

data class NatureDetail(
    var decreasedStat:String? = "",
    var increasedStat:String? = "",
    var hatesFlavor:String? = "",
    var likesFlavor:String? = "",
    var name:String = "",
    var names:List<Name> = listOf(),
)
