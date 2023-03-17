package com.kronos.pokedex.data.remote.move.api

import com.kronos.pokedex.data.remote.move.dto.MoveListDto
import com.kronos.pokedex.data.remote.response_list.ResponseListDto
import retrofit2.Call
import retrofit2.http.GET

interface MoveApi {

    @GET("move")
    fun list(): Call<ResponseListDto<MoveListDto>>
}

