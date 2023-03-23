package com.kronos.pokedex.data.remote.ability.api

import com.kronos.pokedex.data.remote.ability.dto.AbilityInfoDto
import com.kronos.pokedex.data.remote.pokedex.dto.PokedexDto
import com.kronos.pokedex.data.remote.response_list.NamedResourceApiDto
import com.kronos.pokedex.data.remote.response_list.ResponseListDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AbilityApi {

    @GET("ability")
    fun list(@Query("limit")limit:Int, @Query("offset")offset:Int): Call<ResponseListDto<NamedResourceApiDto>>

    @GET("ability/{ability}")
    fun getAbility(@Path("ability")abilityId:Int) : Call<AbilityInfoDto>

    @GET("ability/{ability}")
    fun getAbility(@Path("ability")ability:String) : Call<AbilityInfoDto>

}

