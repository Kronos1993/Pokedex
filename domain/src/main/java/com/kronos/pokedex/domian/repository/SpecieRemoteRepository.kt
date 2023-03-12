package com.kronos.pokedex.domian.repository

import com.kronos.pokedex.domian.model.specie.SpecieInfo

interface SpecieRemoteRepository {
    suspend fun getSpecieInfo(pokemonName:String): SpecieInfo
    suspend fun getSpecieInfo(pokemonId:Int): SpecieInfo
}
