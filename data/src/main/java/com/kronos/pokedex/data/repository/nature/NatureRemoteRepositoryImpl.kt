package com.kronos.pokedex.data.repository.nature

import com.kronos.pokedex.data.data_source.nature.NatureRemoteDataSource
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.ResponseList
import com.kronos.pokedex.domian.model.nature.NatureDetail
import com.kronos.pokedex.domian.repository.NatureRemoteRepository
import javax.inject.Inject

class NatureRemoteRepositoryImpl@Inject constructor(
    private val natureRemoteDataSource: NatureRemoteDataSource
) : NatureRemoteRepository {

    override suspend fun list(limit: Int, offset: Int): ResponseList<NamedResourceApi> {
        return natureRemoteDataSource.listNature(limit, offset)
    }

    override suspend fun getNature(natureId: Int): NatureDetail {
        return natureRemoteDataSource.getNature(natureId)
    }

    override suspend fun getNature(natureName: String): NatureDetail {
        return natureRemoteDataSource.getNature(natureName)
    }

}