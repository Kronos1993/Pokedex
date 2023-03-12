package com.kronos.pokedex.domian.repository

import com.kronos.pokedex.domian.model.type.Type

interface TypesRemoteRepository {
    suspend fun list(): List<Type>
}
