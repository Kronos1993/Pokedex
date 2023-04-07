package com.kronos.pokedex.data.remote.berry

import android.util.Log
import com.kronos.pokedex.data.data_source.berry.BerryRemoteDataSource
import com.kronos.pokedex.data.remote.berry.api.BerryApi
import com.kronos.pokedex.data.remote.berry.mapper.toBerryInfo
import com.kronos.pokedex.data.remote.pokedex.PokedexRemoteDataSourceImpl
import com.kronos.pokedex.data.remote.response_list.mapper.toNamedResource
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.ResponseList
import com.kronos.pokedex.domian.model.item.BerryInfo
import javax.inject.Inject

class BerryRemoteDataSourceImpl @Inject constructor(
    private val berryApi: BerryApi,
) : BerryRemoteDataSource {

    override suspend fun listBerry(limit: Int, offset: Int): ResponseList<NamedResourceApi> {
        var result: ResponseList<NamedResourceApi> =
            try{
                berryApi.list(limit,offset).execute().let {
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
        Log.e(BerryRemoteDataSourceImpl::javaClass.name, "berry list: $result")
        return result
    }

    override suspend fun getBerry(berryId: Int): BerryInfo {
        var result: BerryInfo =
            try {
                berryApi.getBerry(berryId).execute().let {
                    if (it.isSuccessful && it.body() != null) {
                        it.body()!!.toBerryInfo()
                    } else {
                        BerryInfo()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                BerryInfo()
            }
        Log.e(PokedexRemoteDataSourceImpl::javaClass.name, "berry: $result")
        return result
    }

    override suspend fun getBerry(berry: String): BerryInfo {
        var result: BerryInfo =
            try {
                berryApi.getBerry(berry).execute().let {
                    if (it.isSuccessful && it.body() != null) {
                        it.body()!!.toBerryInfo()
                    } else {
                        BerryInfo()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                BerryInfo()
            }
        Log.e(PokedexRemoteDataSourceImpl::javaClass.name, "berry: $result")
        return result
    }
}
