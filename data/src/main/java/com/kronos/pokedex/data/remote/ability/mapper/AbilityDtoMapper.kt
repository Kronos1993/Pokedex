package com.kronos.pokedex.data.remote.ability.mapper

import com.kronos.pokedex.data.remote.ability.dto.AbilityDto
import com.kronos.pokedex.data.remote.response_list.mapper.toNamedResource
import com.kronos.pokedex.domian.model.ability.Ability

fun AbilityDto.toAbility(): Ability =
    Ability(
        ability = ability.toNamedResource(),
        isHidden = isHidden,
    )
