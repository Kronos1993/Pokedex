package com.kronos.pokedex.domian.model

import java.io.Serializable

data class EffectEntry(
    var effect:String = "",
    var shortEffect:String = "",
    var language:String = "",
):Serializable