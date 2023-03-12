package com.kronos.pokedex.data.remote.ability.mapper

import com.kronos.pokedex.data.remote.ability.dto.AbilityInfoDto
import com.kronos.pokedex.domian.model.ability.AbilityInfo

fun AbilityInfoDto.toAbilityInfo(): AbilityInfo =
    AbilityInfo(
        name = name,
        url = url,
    )
