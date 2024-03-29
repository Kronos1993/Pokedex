package com.kronos.pokedex.data.remote.specie.mapper

import com.kronos.pokedex.data.remote.NameDto
import com.kronos.pokedex.data.remote.description.mapper.toFlavorText
import com.kronos.pokedex.data.remote.response_list.mapper.toNamedResource
import com.kronos.pokedex.data.remote.specie.dto.PokemonGeneraDto
import com.kronos.pokedex.data.remote.specie.dto.SpecieInfoDto
import com.kronos.pokedex.data.remote.specie.dto.SpecieVarietiesDto
import com.kronos.pokedex.domian.model.Name
import com.kronos.pokedex.domian.model.specie.PokemonGenera
import com.kronos.pokedex.domian.model.specie.SpecieInfo
import com.kronos.pokedex.domian.model.specie.SpecieVarieties

fun SpecieInfoDto.toSpecieInfo(): SpecieInfo =
    SpecieInfo(
        name = name,
        names = names.map { Name(it.name,it.language) },
        baseHappiness = baseHappiness,
        captureRate = captureRate,
        genderRate = genderRate,
        hatchCounter = hatchCounter,
        evolutionChain = evolutionChain,
        evolvesFrom = evolvesFrom.let{
            if(evolvesFrom!=null)
                evolvesFrom.toNamedResource()
            else
                null
        },
        flavorText = description.map {
            it.toFlavorText()
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
        },
        eggGroup = eggGroups.map {
            it.toNamedResource()
        },
        genera = genera.map {
            it.toPokemonGenera()
        },
    )

fun SpecieVarietiesDto.toSpecieVarieties(): SpecieVarieties =
    SpecieVarieties(
        isDefault = is_default,
        pokemon = pokemon.toNamedResource(),
    )

fun PokemonGeneraDto.toPokemonGenera(): PokemonGenera =
    PokemonGenera(
        genus = genus,
        language = language.name,
    )