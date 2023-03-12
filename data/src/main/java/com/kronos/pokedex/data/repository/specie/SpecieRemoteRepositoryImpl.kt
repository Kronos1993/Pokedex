package com.kronos.pokedex.data.repository.specie

import com.kronos.pokedex.data.data_source.specie.SpecieRemoteDataSource
import com.kronos.pokedex.domian.model.specie.SpecieInfo
import com.kronos.pokedex.domian.repository.SpecieRemoteRepository
import javax.inject.Inject

class SpecieRemoteRepositoryImpl@Inject constructor(
    private val specieRemoteDataSource: SpecieRemoteDataSource
) : SpecieRemoteRepository {

    override suspend fun getSpecieInfo(pokemonName: String): SpecieInfo {
        return specieRemoteDataSource.getSpecie(pokemonName)
    }

    override suspend fun getSpecieInfo(pokemonId: Int): SpecieInfo {
        return specieRemoteDataSource.getSpecie(pokemonId)
    }


}