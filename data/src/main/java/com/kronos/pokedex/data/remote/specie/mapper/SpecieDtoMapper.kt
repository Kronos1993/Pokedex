package com.kronos.pokedex.data.remote.specie.mapper

import com.kronos.pokedex.data.remote.specie.dto.SpecieDto
import com.kronos.pokedex.domian.model.specie.Specie

fun SpecieDto.toSpecie(): Specie =
    Specie(
        name = name,
        url = url
    )