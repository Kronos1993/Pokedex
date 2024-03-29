package com.kronos.pokedex.data.remote.egg_group

import com.kronos.pokedex.data.data_source.egg_group.EggGroupRemoteDataSource
import com.kronos.pokedex.data.remote.egg_group.api.EggGroupApi
import com.kronos.pokedex.data.remote.egg_group.mapper.toEggGroupInfo
import com.kronos.pokedex.data.remote.response_list.mapper.toNamedResource
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.ResponseList
import com.kronos.pokedex.domian.model.egg_group.EggGroupInfo
import javax.inject.Inject

class EggGroupRemoteDataSourceImpl @Inject constructor(
    private val eggGroupApi: EggGroupApi,
) : EggGroupRemoteDataSource {

    override suspend fun listEggGroup(limit: Int, offset: Int): ResponseList<NamedResourceApi> {
        val result: ResponseList<NamedResourceApi> =
            try{
                eggGroupApi.list(limit,offset).execute().let {
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

    override suspend fun getEggGroup(eggGroupId: Int): EggGroupInfo {
        val result: EggGroupInfo =
            try {
                eggGroupApi.getEggGroup(eggGroupId).execute().let {
                    if (it.isSuccessful && it.body() != null) {
                        it.body()!!.toEggGroupInfo()
                    } else {
                        EggGroupInfo()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                EggGroupInfo()
            }
        return result
    }

    override suspend fun getEggGroup(eggGroup: String): EggGroupInfo {
        val result: EggGroupInfo =
            try {
                eggGroupApi.getEggGroup(eggGroup).execute().let {
                    if (it.isSuccessful && it.body() != null) {
                        it.body()!!.toEggGroupInfo()
                    } else {
                        EggGroupInfo()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                EggGroupInfo()
            }
        return result
    }
}
