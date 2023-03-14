/*
 * Copyright (c) 2022. Kronos
 * Created by Marcos Octavio Guerra Liso
 */

package com.kronos.pokedex.data.remote.pokemon.dto

import com.google.gson.annotations.SerializedName

//models the object from pokemon api ws

data class PokemonDexEntryDto(
    @SerializedName("entry_number")
    var entry_number:Int = 0,
    @SerializedName("pokemon_species")
    var pokemon:PokemonListDto = PokemonListDto(),
)
