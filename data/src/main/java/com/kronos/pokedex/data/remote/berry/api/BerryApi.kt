package com.kronos.pokedex.data.remote.berry.api

import com.kronos.pokedex.data.remote.berry.dto.BerryInfoDto
import com.kronos.pokedex.data.remote.response_list.NamedResourceApiDto
import com.kronos.pokedex.data.remote.response_list.ResponseListDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BerryApi {

    @GET("berry")
    fun list(@Query("limit")limit:Int, @Query("offset")offset:Int): Call<ResponseListDto<NamedResourceApiDto>>

    @GET("berry/{berry}")
    fun getBerry(@Path("berry")berryId:Int) : Call<BerryInfoDto>

    @GET("berry/{berry}")
    fun getBerry(@Path("berry")berry:String) : Call<BerryInfoDto>

}

