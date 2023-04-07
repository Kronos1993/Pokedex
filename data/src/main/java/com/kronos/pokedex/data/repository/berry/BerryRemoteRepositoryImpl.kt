package com.kronos.pokedex.data.repository.berry

import com.kronos.pokedex.data.data_source.berry.BerryRemoteDataSource
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.ResponseList
import com.kronos.pokedex.domian.model.item.BerryInfo
import com.kronos.pokedex.domian.repository.BerryRemoteRepository
import javax.inject.Inject

class BerryRemoteRepositoryImpl@Inject constructor(
    private val berryRemoteDataSource: BerryRemoteDataSource
) : BerryRemoteRepository {
    override suspend fun list(limit: Int, offset: Int): ResponseList<NamedResourceApi> {
        return berryRemoteDataSource.listBerry(limit, offset)
    }

    override suspend fun getBerry(berryId: Int): BerryInfo {
        return berryRemoteDataSource.getBerry(berryId)
    }

    override suspend fun getBerry(berryName: String): BerryInfo {
        return berryRemoteDataSource.getBerry(berryName)
    }

}