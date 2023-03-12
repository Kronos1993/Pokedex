package com.kronos.pokedex.domian.repository

import com.kronos.pokedex.domian.model.move.Move
import com.kronos.pokedex.domian.model.pokemon.PokemonInfo
import com.kronos.pokedex.domian.model.pokemon.PokemonList


interface MoveRemoteRepository {
    suspend fun list(): List<Move>
}
