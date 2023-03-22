package com.kronos.pokedex.data.remote.specie.mapper

import com.kronos.pokedex.data.remote.description.mapper.toDescription
import com.kronos.pokedex.data.remote.response_list.mapper.toNamedResource
import com.kronos.pokedex.data.remote.specie.dto.SpecieInfoDto
import com.kronos.pokedex.data.remote.specie.dto.SpecieVarietiesDto
import com.kronos.pokedex.domian.model.specie.SpecieInfo
import com.kronos.pokedex.domian.model.specie.SpecieVarieties

fun SpecieInfoDto.toSpecieInfo(): SpecieInfo =
    SpecieInfo(
        name = name,
        baseHappiness = baseHappiness,
        captureRate = captureRate,
        evolutionChain = evolutionChain,
        evolvesFrom = evolvesFrom.let{
            if(evolvesFrom!=null)
                evolvesFrom.toNamedResource()
            else
                null
        },
        description = description.map {
            it.toDescription()
        },
        growthRate = growthRate.toNamedResource(),
        habitat = habitat.let{
            if(habitat!=null)
                habitat.toNamedResource()
            else
                null
        },
        hasGenderDifferences = hasGenderDifferences,
        isBaby = isBaby,
        isLegendary = isLegendary,
        isMythical = isMythical,
        varieties = varieties.map {
            it.toSpecieVarieties()
        }
    )

fun SpecieVarietiesDto.toSpecieVarieties(): SpecieVarieties =
    SpecieVarieties(
        isDefault = is_default,
        pokemon = pokemon.toNamedResource(),
    )