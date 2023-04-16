package com.kronos.pokedex.data.remote.pokemon.api

import com.kronos.pokedex.data.remote.pokemon.dto.PokemonInfoDto
import com.kronos.pokedex.data.remote.response_list.NamedResourceApiDto
import com.kronos.pokedex.data.remote.response_list.ResponseListDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {

    @GET("pokemon")
    fun list(@Query("limit")limit:Int,@Query("offset")offset:Int): Call<ResponseListDto<NamedResourceApiDto>>

    @GET("pokemon/{pokemon}")
    fun getPokemonInfo(@Path("pokemon")pokemonName:String): Call<PokemonInfoDto>

    @GET("pokemon/{pokemon}")
    fun getPokemonInfo(@Path("pokemon")pokemonId:Int): Call<PokemonInfoDto
            >


}

