package com.kronos.pokedex.data.data_source.pokemon
import com.kronos.pokedex.domian.model.ResponseList
import com.kronos.pokedex.domian.model.pokemon.PokemonInfo
import com.kronos.pokedex.domian.model.pokemon.PokemonList


interface PokemonRemoteDataSource {
    suspend fun listPokemon(limit:Int = 20,offset:Int = 0): ResponseList<PokemonList>

    fun listPokemon(limit:Int = 20,offset:Int = 0,callback:Any)

    suspend fun getPokemonInfo(pokemonId: Int = 1):PokemonInfo

    suspend fun getPokemonInfo(pokemonName: String):PokemonInfo

    fun getPokemonInfo(pokemonId: Int = 1,callback:Any)

    fun getPokemonInfo(pokemonName: String,callback:Any)
}