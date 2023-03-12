package com.kronos.pokedex.domian.repository

import com.kronos.pokedex.domian.model.ability.Ability
import com.kronos.pokedex.domian.model.pokemon.PokemonInfo
import com.kronos.pokedex.domian.model.pokemon.PokemonList


interface AbilityRemoteRepository {
    suspend fun list(): List<Ability>
}
