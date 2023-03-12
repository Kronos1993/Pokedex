package com.kronos.pokedex.data.remote.specie.api

import com.kronos.pokedex.data.remote.specie.dto.SpecieInfoDto
import com.kronos.pokedex.domian.model.specie.SpecieInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface SpecieApi {

    @GET("pokemon-species/{pokemon}")
    fun getSpecieInfo(@Path("pokemon")pokemonName:String): Call<SpecieInfoDto>

    @GET("pokemon-species/{pokemon}")
    fun getSpecieInfo(@Path("pokemon")pokemonId:Int): Call<SpecieInfoDto>
}

