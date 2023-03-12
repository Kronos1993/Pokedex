package com.kronos.pokedex.data.remote.ability.api

import com.kronos.pokedex.data.remote.ability.dto.AbilityDto
import com.kronos.pokedex.data.remote.response_list.ResponseListDto
import retrofit2.Call
import retrofit2.http.GET

interface AbilityApi {

    @GET("ability")
    fun list(): Call<ResponseListDto<AbilityDto>>
}

