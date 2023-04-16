package com.kronos.pokedex.data.remote.item

import android.util.Log
import com.kronos.pokedex.data.data_source.berry.BerryRemoteDataSource
import com.kronos.pokedex.data.data_source.item.ItemRemoteDataSource
import com.kronos.pokedex.data.remote.berry.api.BerryApi
import com.kronos.pokedex.data.remote.berry.mapper.toBerryInfo
import com.kronos.pokedex.data.remote.item.api.ItemApi
import com.kronos.pokedex.data.remote.item.mapper.toItemInfo
import com.kronos.pokedex.data.remote.pokedex.PokedexRemoteDataSourceImpl
import com.kronos.pokedex.data.remote.response_list.mapper.toNamedResource
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.ResponseList
import com.kronos.pokedex.domian.model.item.BerryInfo
import com.kronos.pokedex.domian.model.item.ItemInfo
import javax.inject.Inject

class ItemRemoteDataSourceImpl @Inject constructor(
    private val itemApi: ItemApi,
) : ItemRemoteDataSource {

    override suspend fun listItem(limit: Int, offset: Int): ResponseList<NamedResourceApi> {
        var result: ResponseList<NamedResourceApi> =
            try{
                itemApi.list(limit,offset).execute().let {
                    if (it.isSuccessful && it.body() != null) {
                        var response = it.body()!!
                        ResponseList(response.count, response.next, response.results.map {
                            it.toNamedResource()
                        })
                    } else {
                        ResponseList()
                    }
                }
            }catch (e:Exception){
                e.printStackTrace()
                ResponseList()
            }
        Log.e(ItemRemoteDataSourceImpl::javaClass.name, "item list: $result")
        return result
    }

    override suspend fun getItem(itemId: Int): ItemInfo {
        var result: ItemInfo =
            try {
                itemApi.getItem(itemId).execute().let {
                    if (it.isSuccessful && it.body() != null) {
                        it.body()!!.toItemInfo()
                    } else {
                        ItemInfo()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                ItemInfo()
            }
        Log.e(PokedexRemoteDataSourceImpl::javaClass.name, "item: $result")
        return result
    }

    override suspend fun getItem(item: String): ItemInfo {
        var result: ItemInfo =
            try {
                itemApi.getItem(item).execute().let {
                    if (it.isSuccessful && it.body() != null) {
                        it.body()!!.toItemInfo()
                    } else {
                        ItemInfo()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                ItemInfo()
            }
        Log.e(PokedexRemoteDataSourceImpl::javaClass.name, "item: $result")
        return result
    }
}
