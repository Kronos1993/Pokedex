package com.kronos.pokedex.domian.repository

import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.ResponseList
import com.kronos.pokedex.domian.model.item.ItemCategory
import com.kronos.pokedex.domian.model.item.ItemInfo


interface ItemRemoteRepository {
    suspend fun listItems(limit:Int, offset:Int): ResponseList<NamedResourceApi>

    suspend fun getItem(itemId: Int): ItemInfo

    suspend fun getItem(itemName: String): ItemInfo

    suspend fun listItemCategories(limit:Int,offset:Int): ResponseList<NamedResourceApi>

    suspend fun getItemCategory(item: String): ItemCategory

    suspend fun getItemCategory(itemId: Int): ItemCategory
}
