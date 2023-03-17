package com.kronos.pokedex.domian.model.ability

import com.kronos.pokedex.domian.model.NamedResourceApi
import java.io.Serializable

data class Ability(
    val ability: NamedResourceApi = NamedResourceApi(),
    val isHidden:Boolean = true,
):Serializable
