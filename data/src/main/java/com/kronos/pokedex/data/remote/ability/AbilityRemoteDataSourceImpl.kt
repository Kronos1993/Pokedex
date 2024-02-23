package com.kronos.pokedex.data.remote.ability

import com.kronos.pokedex.data.data_source.ability.AbilityRemoteDataSource
import com.kronos.pokedex.data.remote.ability.api.AbilityApi
import com.kronos.pokedex.data.remote.ability.mapper.toAbilityInfo
import com.kronos.pokedex.data.remote.response_list.mapper.toNamedResource
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.ResponseList
import com.kronos.pokedex.domian.model.ability.AbilityInfo
import javax.inject.Inject

class AbilityRemoteDataSourceImpl @Inject constructor(
    private val abilityApi: AbilityApi,
) : AbilityRemoteDataSource {

    override suspend fun listAbility(limit: Int, offset: Int): ResponseList<NamedResourceApi> {
        val result: ResponseList<NamedResourceApi> =
            try{
                abilityApi.list(limit,offset).execute().let {
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

    override suspend fun getAbility(abilityId: Int): AbilityInfo {
        val result: AbilityInfo =
            try {
                abilityApi.getAbility(abilityId).execute().let {
                    if (it.isSuccessful && it.body() != null) {
                        it.body()!!.toAbilityInfo()
                    } else {
                        AbilityInfo()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                AbilityInfo()
            }
        return result
    }

    override suspend fun getAbility(ability: String): AbilityInfo {
        val result: AbilityInfo =
            try {
                abilityApi.getAbility(ability).execute().let {
                    if (it.isSuccessful && it.body() != null) {
                        it.body()!!.toAbilityInfo()
                    } else {
                        AbilityInfo()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                AbilityInfo()
            }
        return result
    }
}
