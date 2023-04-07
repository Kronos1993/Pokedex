package com.kronos.pokedex.data.repository.item

import com.kronos.pokedex.data.data_source.item.ItemRemoteDataSource
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.ResponseList
import com.kronos.pokedex.domian.model.item.ItemInfo
import com.kronos.pokedex.domian.repository.ItemRemoteRepository
import javax.inject.Inject

class ItemRemoteRepositoryImpl@Inject constructor(
    private val itemRemoteDataSource: ItemRemoteDataSource
) : ItemRemoteRepository {
    override suspend fun list(limit: Int, offset: Int): ResponseList<NamedResourceApi> {
        return itemRemoteDataSource.listItem(limit, offset)
    }

    override suspend fun getItem(itemId: Int): ItemInfo {
        return itemRemoteDataSource.getItem(itemId)
    }

    override suspend fun getItem(itemName: String): ItemInfo {
        return itemRemoteDataSource.getItem(itemName)
    }

}