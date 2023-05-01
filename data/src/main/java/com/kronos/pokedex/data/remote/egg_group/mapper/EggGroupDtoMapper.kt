package com.kronos.pokedex.data.remote.egg_group.mapper

import com.kronos.pokedex.data.remote.egg_group.dto.EggGroupInfoDto
import com.kronos.pokedex.data.remote.response_list.mapper.toNamedResource
import com.kronos.pokedex.domian.model.Name
import com.kronos.pokedex.domian.model.ability.AbilityInfo
import com.kronos.pokedex.domian.model.egg_group.EggGroupInfo

fun EggGroupInfoDto.toEggGroupInfo(): EggGroupInfo =
    EggGroupInfo(
        id = id,
        name = name,
        names = names.map { Name(it.name,it.language) },
        pokemonSpecies = pokemonSpecies.map { it.toNamedResource() }
    )
