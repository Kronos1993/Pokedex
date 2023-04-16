package com.kronos.pokedex.data.remote.pokemon.mapper

import com.kronos.pokedex.data.remote.ability.mapper.toAbility
import com.kronos.pokedex.data.remote.move.mapper.toMoveList
import com.kronos.pokedex.data.remote.pokemon.dto.PokemonInfoDto
import com.kronos.pokedex.data.remote.sprite.mapper.toSprite
import com.kronos.pokedex.data.remote.stat.mapper.toStat
import com.kronos.pokedex.data.remote.type.mapper.toType
import com.kronos.pokedex.domian.model.pokemon.PokemonInfo
import com.kronos.pokedex.domian.model.specie.SpecieInfo

fun PokemonInfoDto.toPokemonInfo(): PokemonInfo =
    PokemonInfo(
        id = id,
        abilities = abilities.map {
            it.toAbility()
        },
        name = name,
        height = height,
        weight = weight,
        sprites = sprites.toSprite(),
        baseExperience = baseExperience,
        types = types.map {
            it.toType()
        },
        stats = stats.map {
            it.toStat()
        },
        moves = moves.map {
            it.toMoveList()
        },
        specie = SpecieInfo()
    )

fun PokemonInfoDto.toPokemonInfo(specieInfo:SpecieInfo): PokemonInfo =
    PokemonInfo(
        id = id,
        abilities = abilities.map {
            it.toAbility()
        },
        name = name,
        height = height,
        weight = weight,
        sprites = sprites.toSprite(),
        baseExperience = baseExperience,
        types = types.map {
            it.toType()
        },
        stats = stats.map {
            it.toStat()
        },
        moves = moves.map {
            it.toMoveList()
        },
        specie = specieInfo
    )