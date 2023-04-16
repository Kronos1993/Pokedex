package com.kronos.pokedex.domian.repository

import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.ResponseList
import com.kronos.pokedex.domian.model.nature.NatureDetail


interface NatureRemoteRepository {
    suspend fun list(limit:Int,offset:Int): ResponseList<NamedResourceApi>

    suspend fun getNature(natureId: Int): NatureDetail

    suspend fun getNature(natureName: String): NatureDetail
}
