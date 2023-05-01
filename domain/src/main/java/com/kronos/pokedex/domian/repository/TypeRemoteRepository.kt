package com.kronos.pokedex.domian.repository

import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.ResponseList
import com.kronos.pokedex.domian.model.type.TypeInfo

interface TypeRemoteRepository {
    suspend fun list(limit: Int, offset: Int): ResponseList<NamedResourceApi>

    suspend fun getTypeInfo(typeId: Int): TypeInfo

    suspend fun getTypeInfo(typeName: String): TypeInfo
}
