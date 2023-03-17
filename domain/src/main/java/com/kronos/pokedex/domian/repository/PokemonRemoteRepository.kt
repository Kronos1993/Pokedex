package com.kronos.pokedex.domian.repository

import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.ResponseList
import com.kronos.pokedex.domian.model.pokemon.PokemonInfo


interface PokemonRemoteRepository {
    suspend fun listPokemon(limit:Int,offset:Int): ResponseList<NamedResourceApi>

    fun getPokemonInfo(pokemonId: Int,callback:Any)

    suspend fun getPokemonInfo(pokemonId: Int): PokemonInfo

    suspend fun getPokemonInfo(pokemonName: String):PokemonInfo

    fun getPokemonInfo(pokemonName: String,callback:Any)
}
