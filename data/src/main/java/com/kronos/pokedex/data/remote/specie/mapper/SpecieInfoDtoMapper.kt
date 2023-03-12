package com.kronos.pokedex.data.remote.specie.mapper

import com.kronos.pokedex.data.remote.description.mapper.toDescription
import com.kronos.pokedex.data.remote.specie.dto.SpecieInfoDto
import com.kronos.pokedex.domian.model.evolution_chain.EvolutionChain
import com.kronos.pokedex.domian.model.specie.SpecieInfo

fun SpecieInfoDto.toSpecieInfo(): SpecieInfo =
    SpecieInfo(
        name = name,
        baseHappiness = baseHappiness,
        captureRate = captureRate,
        evolutionChain = EvolutionChain(),
        evolvesFrom = evolvesFrom.let{
            if(evolvesFrom!=null)
                evolvesFrom.toSpecie()
            else
                null
        },
        description = description.map {
            it.toDescription()
        },
        growthRate = growthRate.toSpecieGrowthRate(),
        habitat = habitat.let{
            if(habitat!=null)
                habitat.toSpecieHabitat()
            else
                null
        },
        hasGenderDifferences = hasGenderDifferences,
        isBaby = isBaby,
        isLegendary = isLegendary,
        isMythical = isMythical,
    )