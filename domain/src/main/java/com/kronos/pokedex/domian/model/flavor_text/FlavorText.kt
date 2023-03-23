package com.kronos.pokedex.domian.model.flavor_text

import java.io.Serializable

data class FlavorText(
    var description:String = "",
    var language:String = "",
): Serializable