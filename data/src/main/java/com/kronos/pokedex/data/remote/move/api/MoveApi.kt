package com.kronos.pokedex.data.remote.move.api

import com.kronos.pokedex.data.remote.move.dto.MoveInfoDto
import com.kronos.pokedex.data.remote.response_list.NamedResourceApiDto
import com.kronos.pokedex.data.remote.response_list.ResponseListDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoveApi {

    @GET("move")
    fun list(@Query("limit")limit:Int, @Query("offset")offset:Int): Call<ResponseListDto<NamedResourceApiDto>>

    @GET("move/{move}")
    fun getMove(@Path("move")moveId:Int) : Call<MoveInfoDto>

    @GET("move/{move}")
    fun getMove(@Path("move")move:String) : Call<MoveInfoDto>
}

