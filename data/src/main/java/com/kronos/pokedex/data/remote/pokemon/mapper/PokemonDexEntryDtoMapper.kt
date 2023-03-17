package com.kronos.pokedex.data.remote.pokemon.mapper

import com.kronos.pokedex.data.remote.pokemon.dto.PokemonDexEntryDto
import com.kronos.pokedex.data.remote.response_list.mapper.toNamedResource
import com.kronos.pokedex.domian.model.pokemon.PokemonDexEntry

fun PokemonDexEntryDto.toPokemonDexEntry(): PokemonDexEntry =
    PokemonDexEntry(
        dexEntry = entry_number,
        pokemon = pokemon.toNamedResource()

    )