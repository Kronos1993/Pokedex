package com.kronos.pokedex.domian.model.pokedex

import com.kronos.pokedex.domian.model.pokemon.PokemonDexEntry

data class Pokedex(
    var id:String = "",
    var name:String = "",
    var pokemons:List<PokemonDexEntry> = listOf()
)