package com.kronos.pokedex.data.data_source.type

import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.ResponseList
import com.kronos.pokedex.domian.model.type.TypeInfo

interface TypeRemoteDataSource {
    suspend fun listType(limit:Int = 20,offset:Int = 0): ResponseList<NamedResourceApi>

    suspend fun getTypeInfo(typeId: Int = 1): TypeInfo

    suspend fun getTypeInfo(type: String): TypeInfo
}