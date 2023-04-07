package com.kronos.pokedex.data.remote.item.api

import com.kronos.pokedex.data.remote.item.dto.ItemInfoDto
import com.kronos.pokedex.data.remote.response_list.NamedResourceApiDto
import com.kronos.pokedex.data.remote.response_list.ResponseListDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ItemApi {

    @GET("item")
    fun list(@Query("limit")limit:Int, @Query("offset")offset:Int): Call<ResponseListDto<NamedResourceApiDto>>

    @GET("item/{item}")
    fun getItem(@Path("item")itemId:Int) : Call<ItemInfoDto>

    @GET("item/{item}")
    fun getItem(@Path("item")item:String) : Call<ItemInfoDto>

}

