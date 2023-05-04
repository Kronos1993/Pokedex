package com.kronos.pokedex.data.remote.egg_group

import android.util.Log
import com.kronos.pokedex.data.data_source.ability.AbilityRemoteDataSource
import com.kronos.pokedex.data.data_source.egg_group.EggGroupRemoteDataSource
import com.kronos.pokedex.data.remote.ability.api.AbilityApi
import com.kronos.pokedex.data.remote.ability.mapper.toAbilityInfo
import com.kronos.pokedex.data.remote.egg_group.api.EggGroupApi
import com.kronos.pokedex.data.remote.egg_group.mapper.toEggGroupInfo
import com.kronos.pokedex.data.remote.pokedex.PokedexRemoteDataSourceImpl
import com.kronos.pokedex.data.remote.response_list.mapper.toNamedResource
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.ResponseList
import com.kronos.pokedex.domian.model.ability.AbilityInfo
import com.kronos.pokedex.domian.model.egg_group.EggGroupInfo
import javax.inject.Inject

class EggGroupRemoteDataSourceImpl @Inject constructor(
    private val eggGroupApi: EggGroupApi,
) : EggGroupRemoteDataSource {

    override suspend fun listEggGroup(limit: Int, offset: Int): ResponseList<NamedResourceApi> {
        var result: ResponseList<NamedResourceApi> =
            try{
                eggGroupApi.list(limit,offset).execute().let {
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
        Log.e(EggGroupRemoteDataSourceImpl::javaClass.name, "egg group list: $result")
        return result
    }

    override suspend fun getEggGroup(eggGroupId: Int): EggGroupInfo {
        var result: EggGroupInfo =
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
        Log.e(EggGroupRemoteDataSourceImpl::javaClass.name, "egg group: $result")
        return result
    }

    override suspend fun getEggGroup(eggGroup: String): EggGroupInfo {
        var result: EggGroupInfo =
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
        Log.e(EggGroupRemoteDataSourceImpl::javaClass.name, "egg group: $result")
        return result
    }
}
