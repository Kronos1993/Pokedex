package com.kronos.pokedex.data.remote.pokedex.mapper

import com.kronos.pokedex.data.remote.pokedex.dto.PokedexDto
import com.kronos.pokedex.data.remote.pokemon.mapper.toPokemonDexEntry
import com.kronos.pokedex.domian.model.Name
import com.kronos.pokedex.domian.model.pokedex.Pokedex

fun PokedexDto.toPokedex(): Pokedex =
    Pokedex(
        id = id,
        name = name,
        names = names.map { Name(it.name,it.language) },
        pokemons = pokemons.map {
            it.toPokemonDexEntry()
        }

    )