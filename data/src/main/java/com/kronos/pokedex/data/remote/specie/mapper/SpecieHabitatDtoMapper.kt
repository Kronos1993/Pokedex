package com.kronos.pokedex.data.remote.specie.mapper

import com.kronos.pokedex.data.remote.specie.dto.SpecieHabitatDto
import com.kronos.pokedex.domian.model.specie.SpecieHabitat

fun SpecieHabitatDto.toSpecieHabitat(): SpecieHabitat =
    SpecieHabitat(
        name = name,
        url = url
    )