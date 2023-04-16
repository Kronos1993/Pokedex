package com.kronos.pokedex.data.remote.pokedex.dto

import com.google.gson.annotations.SerializedName
import com.kronos.pokedex.data.remote.pokemon.dto.PokemonDexEntryDto

data class PokedexDto(
    @SerializedName("id")
    var id:String = "",
    @SerializedName("name")
    var name:String = "",
    @SerializedName("pokemon_entries")
    var pokemons:List<PokemonDexEntryDto> = listOf()
)