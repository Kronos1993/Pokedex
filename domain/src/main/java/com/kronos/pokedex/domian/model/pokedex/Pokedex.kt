package com.kronos.pokedex.domian.model.pokedex

import com.kronos.pokedex.domian.model.Name
import com.kronos.pokedex.domian.model.pokemon.PokemonDexEntry

data class Pokedex(
    var id:String = "",
    var name:String = "",
    var names:List<Name> = listOf(),
    var pokemons:List<PokemonDexEntry> = listOf()
)