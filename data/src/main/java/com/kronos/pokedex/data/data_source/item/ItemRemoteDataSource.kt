package com.kronos.pokedex.data.data_source.item

import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.ResponseList
import com.kronos.pokedex.domian.model.item.ItemInfo

interface ItemRemoteDataSource {
    suspend fun listItem(limit:Int = 20,offset:Int = 0): ResponseList<NamedResourceApi>

    suspend fun getItem(itemId: Int = 1): ItemInfo

    suspend fun getItem(item: String): ItemInfo
}