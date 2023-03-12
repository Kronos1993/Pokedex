package com.kronos.pokedex.data.remote.specie.mapper

import com.kronos.pokedex.data.remote.specie.dto.SpecieGrowthRateDto
import com.kronos.pokedex.data.remote.specie.dto.SpecieHabitatDto
import com.kronos.pokedex.domian.model.specie.SpecieGrowthRate
import com.kronos.pokedex.domian.model.specie.SpecieHabitat

fun SpecieGrowthRateDto.toSpecieGrowthRate(): SpecieGrowthRate =
    SpecieGrowthRate(
        name = name,
        url = url
    )