package com.kronos.pokedex.data.data_source.specie

import com.kronos.pokedex.domian.model.specie.SpecieInfo

interface SpecieRemoteDataSource {
    suspend fun getSpecie(pokemonName:String): SpecieInfo
    suspend fun getSpecie(pokemonId:Int): SpecieInfo
}