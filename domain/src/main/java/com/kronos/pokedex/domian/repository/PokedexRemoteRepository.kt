package com.kronos.pokedex.domian.repository

import com.kronos.pokedex.domian.model.ResponseList
import com.kronos.pokedex.domian.model.ResponseListItem
import com.kronos.pokedex.domian.model.pokedex.Pokedex
import com.kronos.pokedex.domian.model.pokemon.PokemonInfo
import com.kronos.pokedex.domian.model.pokemon.PokemonList


interface PokedexRemoteRepository {
    suspend fun list(limit:Int,offset:Int): ResponseList<ResponseListItem>

    suspend fun getPokedex(pokedexId: Int): Pokedex

    suspend fun getPokedex(pokedexName: String):Pokedex
}
