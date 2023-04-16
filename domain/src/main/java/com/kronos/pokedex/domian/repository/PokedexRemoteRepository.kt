package com.kronos.pokedex.domian.repository

import com.kronos.pokedex.domian.model.ResponseList
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.pokedex.Pokedex


interface PokedexRemoteRepository {
    suspend fun list(limit:Int,offset:Int): ResponseList<NamedResourceApi>

    suspend fun getPokedex(pokedexId: Int): Pokedex

    suspend fun getPokedex(pokedexName: String):Pokedex
}
