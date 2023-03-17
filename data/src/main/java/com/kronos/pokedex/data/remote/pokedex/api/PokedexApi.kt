package com.kronos.pokedex.data.remote.pokedex.api

import com.kronos.pokedex.data.remote.pokedex.dto.PokedexDto
import com.kronos.pokedex.data.remote.response_list.ResponseListDto
import com.kronos.pokedex.data.remote.response_list.NamedResourceApiDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokedexApi {

    @GET("pokedex")
    fun list(@Query("limit")limit:Int, @Query("offset")offset:Int): Call<ResponseListDto<NamedResourceApiDto>>

    @GET("pokedex/{pokedex}")
    fun getPokedex(@Path("pokedex")pokedexId:Int) : Call<PokedexDto>

    @GET("pokedex/{pokedex}")
    fun getPokedex(@Path("pokedex")pokedex:String) : Call<PokedexDto>
}

