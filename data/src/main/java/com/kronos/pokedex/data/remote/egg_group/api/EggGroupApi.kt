package com.kronos.pokedex.data.remote.egg_group.api

import com.kronos.pokedex.data.remote.egg_group.dto.EggGroupInfoDto
import com.kronos.pokedex.data.remote.response_list.NamedResourceApiDto
import com.kronos.pokedex.data.remote.response_list.ResponseListDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EggGroupApi {

    @GET("egg-group")
    fun list(@Query("limit")limit:Int, @Query("offset")offset:Int): Call<ResponseListDto<NamedResourceApiDto>>

    @GET("egg-group/{eggGroup}")
    fun getEggGroup(@Path("eggGroup")eggGroup:Int) : Call<EggGroupInfoDto>

    @GET("egg-group/{eggGroup}")
    fun getEggGroup(@Path("eggGroup")eggGroup:String) : Call<EggGroupInfoDto>

}

