package com.kronos.pokedex.data.data_source.pokedex
import com.kronos.pokedex.domian.model.ResponseList
import com.kronos.pokedex.domian.model.ResponseListItem
import com.kronos.pokedex.domian.model.pokedex.Pokedex


interface PokedexRemoteDataSource {
    suspend fun list(limit:Int = 20,offset:Int = 0): ResponseList<ResponseListItem>

    suspend fun getPokedex(pokedexId: Int = 1):Pokedex

    suspend fun getPokedex(pokedex: String):Pokedex
}