package com.kronos.pokedex.domian.repository

import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.ResponseList
import com.kronos.pokedex.domian.model.item.ItemInfo


interface ItemRemoteRepository {
    suspend fun list(limit:Int,offset:Int): ResponseList<NamedResourceApi>

    suspend fun getItem(itemId: Int): ItemInfo

    suspend fun getItem(itemName: String): ItemInfo
}
