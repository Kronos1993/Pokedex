package com.kronos.pokedex.domian.repository

import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.ResponseList
import com.kronos.pokedex.domian.model.item.BerryInfo


interface BerryRemoteRepository {
    suspend fun list(limit:Int,offset:Int): ResponseList<NamedResourceApi>

    suspend fun getBerry(berryId: Int): BerryInfo

    suspend fun getBerry(berryName: String): BerryInfo
}
