package com.kronos.pokedex.data.remote.pokemon.mapper

import com.kronos.pokedex.data.remote.pokemon.dto.PokemonListDto
import com.kronos.pokedex.domian.model.pokemon.PokemonList

fun PokemonListDto.toPokemonList(): PokemonList =
    PokemonList(
        name = name,
        url = url
    )
