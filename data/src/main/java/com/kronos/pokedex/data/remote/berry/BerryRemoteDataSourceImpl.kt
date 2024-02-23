package com.kronos.pokedex.data.remote.berry

import com.kronos.pokedex.data.data_source.berry.BerryRemoteDataSource
import com.kronos.pokedex.data.remote.berry.api.BerryApi
import com.kronos.pokedex.data.remote.berry.mapper.toBerryInfo
import com.kronos.pokedex.data.remote.response_list.mapper.toNamedResource
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.ResponseList
import com.kronos.pokedex.domian.model.item.BerryInfo
import javax.inject.Inject

class BerryRemoteDataSourceImpl @Inject constructor(
    private val berryApi: BerryApi,
) : BerryRemoteDataSource {

    override suspend fun listBerry(limit: Int, offset: Int): ResponseList<NamedResourceApi> {
        val result: ResponseList<NamedResourceApi> =
            try{
                berryApi.list(limit,offset).execute().let {
                    if (it.isSuccessful && it.body() != null) {
                        val response = it.body()!!
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
        return result
    }

    override suspend fun getBerry(berryId: Int): BerryInfo {
        val result: BerryInfo =
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
        return result
    }

    override suspend fun getBerry(berry: String): BerryInfo {
        val result: BerryInfo =
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
        return result
    }
}
