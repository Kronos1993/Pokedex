package com.kronos.pokedex.data.data_source.berry

import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.ResponseList
import com.kronos.pokedex.domian.model.item.BerryInfo

interface BerryRemoteDataSource {
    suspend fun listBerry(limit:Int = 20,offset:Int = 0): ResponseList<NamedResourceApi>

    suspend fun getBerry(berryId: Int = 1): BerryInfo

    suspend fun getBerry(berry: String): BerryInfo
}