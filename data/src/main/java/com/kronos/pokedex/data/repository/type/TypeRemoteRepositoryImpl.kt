package com.kronos.pokedex.data.repository.type

import com.kronos.pokedex.data.data_source.type.TypeRemoteDataSource
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.ResponseList
import com.kronos.pokedex.domian.model.type.TypeInfo
import com.kronos.pokedex.domian.repository.TypeRemoteRepository
import javax.inject.Inject

class TypeRemoteRepositoryImpl@Inject constructor(
    private val typeRemoteDataSource: TypeRemoteDataSource
) : TypeRemoteRepository {
    override suspend fun list(limit: Int, offset: Int): ResponseList<NamedResourceApi> {
        return typeRemoteDataSource.listType(limit, offset)
    }

    override suspend fun getTypeInfo(typeId: Int): TypeInfo {
        return typeRemoteDataSource.getTypeInfo(typeId)
    }

    override suspend fun getTypeInfo(typeName: String): TypeInfo {
        return typeRemoteDataSource.getTypeInfo(typeName)
    }

}