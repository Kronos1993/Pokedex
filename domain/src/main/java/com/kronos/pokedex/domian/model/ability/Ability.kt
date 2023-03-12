package com.kronos.pokedex.domian.model.ability

import java.io.Serializable

data class Ability(
    val ability: AbilityInfo = AbilityInfo(),
    val isHidden:Boolean = true,
):Serializable
