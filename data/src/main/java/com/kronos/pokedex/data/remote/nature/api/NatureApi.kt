package com.kronos.pokedex.data.remote.nature.api

import com.kronos.pokedex.data.remote.move.dto.MoveInfoDto
import com.kronos.pokedex.data.remote.nature.dto.NatureDetailDto
import com.kronos.pokedex.data.remote.response_list.NamedResourceApiDto
import com.kronos.pokedex.data.remote.response_list.ResponseListDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NatureApi {

    @GET("nature")
    fun list(@Query("limit")limit:Int, @Query("offset")offset:Int): Call<ResponseListDto<NamedResourceApiDto>>

    @GET("nature/{nature}")
    fun getNature(@Path("nature")natureId:Int) : Call<NatureDetailDto>

    @GET("nature/{nature}")
    fun getNature(@Path("nature")nature:String) : Call<NatureDetailDto>
}

