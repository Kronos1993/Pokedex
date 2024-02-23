package com.kronos.pokedex.data.data_source.pokemon
import com.kronos.pokedex.data.remote.pokemon.dto.PokemonInfoDto
import com.kronos.pokedex.data.remote.response_list.NamedResourceApiDto
import com.kronos.pokedex.data.remote.response_list.ResponseListDto
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.ResponseList
import com.kronos.pokedex.domian.model.pokemon.PokemonInfo
import retrofit2.Callback


interface PokemonRemoteDataSource {
    suspend fun listPokemon(limit:Int = 20,offset:Int = 0): ResponseList<NamedResourceApi>

    fun listPokemon(limit:Int = 20,offset:Int = 0,callback:Callback<ResponseListDto<NamedResourceApiDto>>)

    suspend fun getPokemonInfo(pokemonId: Int = 1):PokemonInfo

    suspend fun getPokemonInfo(pokemonName: String):PokemonInfo

    fun getPokemonInfo(pokemonId: Int = 1,callback: Callback<PokemonInfoDto>)

    fun getPokemonInfo(pokemonName: String,callback:Callback<PokemonInfoDto>)
}