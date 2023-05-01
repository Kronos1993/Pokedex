package com.kronos.pokedex.data.remote.type.api

import com.kronos.pokedex.data.remote.response_list.NamedResourceApiDto
import com.kronos.pokedex.data.remote.response_list.ResponseListDto
import com.kronos.pokedex.data.remote.type.dto.TypeInfoDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TypeApi {

    @GET("type")
    fun list(@Query("limit")limit:Int, @Query("offset")offset:Int): Call<ResponseListDto<NamedResourceApiDto>>

    @GET("type/{type}")
    fun getTypeInfo(@Path("type")type:Int) : Call<TypeInfoDto>

    @GET("type/{type}")
    fun getTypeInfo(@Path("type")type:String) : Call<TypeInfoDto>

}

