package com.kronos.pokedex.data.repository.pokedex

import com.kronos.pokedex.data.data_source.pokedex.PokedexRemoteDataSource
import com.kronos.pokedex.domian.model.ResponseList
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.pokedex.Pokedex
import com.kronos.pokedex.domian.repository.PokedexRemoteRepository
import javax.inject.Inject

class PokedexRemoteRepositoryImpl@Inject constructor(
    private val pokedexRemoteDataSource: PokedexRemoteDataSource
) : PokedexRemoteRepository {
    override suspend fun list(limit: Int, offset: Int): ResponseList<NamedResourceApi> {
        return pokedexRemoteDataSource.list(limit,offset)
    }

    override suspend fun getPokedex(pokedexId: Int): Pokedex {
        return pokedexRemoteDataSource.getPokedex(pokedexId)
    }

    override suspend fun getPokedex(pokedexName: String): Pokedex {
        return pokedexRemoteDataSource.getPokedex(pokedexName)
    }
}