package com.kronos.pokedex.data.remote.evolution_chain.api

import com.kronos.pokedex.data.remote.evolution_chain.dto.ChainLinkDto
import com.kronos.pokedex.data.remote.evolution_chain.dto.EvolutionChainDto
import com.kronos.pokedex.data.remote.move.dto.MoveListDto
import com.kronos.pokedex.data.remote.pokedex.dto.PokedexDto
import com.kronos.pokedex.data.remote.response_list.NamedResourceApiDto
import com.kronos.pokedex.data.remote.response_list.ResponseListDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EvolutionChainApi {

    @GET("evolution-chain")
    fun list(@Query("limit")limit:Int, @Query("offset")offset:Int): Call<ResponseListDto<NamedResourceApiDto>>

    @GET("evolution-chain/{chain}")
    fun getEvolutionChain(@Path("chain")chainId:Int) : Call<EvolutionChainDto>

}

