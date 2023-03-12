package com.kronos.pokedex.data.remote.ability

import android.util.Log
import com.kronos.pokedex.data.data_source.ability.AbilityRemoteDataSource
import com.kronos.pokedex.data.remote.ability.api.AbilityApi
import com.kronos.pokedex.data.remote.ability.mapper.toAbility
import com.kronos.pokedex.domian.model.ability.Ability
import javax.inject.Inject

class AbilityRemoteDatasourceImpl @Inject constructor(
    private val abilityApi: AbilityApi,
) : AbilityRemoteDataSource {

    override suspend fun listAbility(): List<Ability> {
        var result: List<Ability> =
            try{
                abilityApi.list().execute().let {
                    if (it.isSuccessful && it.body() != null) {
                        it.body()!!.results.map {abilityDao->
                            abilityDao.toAbility()
                        }
                    } else {
                        listOf()
                    }
                }
            }catch (e:Exception){
                e.printStackTrace()
                listOf()
            }
        Log.e(AbilityRemoteDatasourceImpl::javaClass.name, "ability list: $result")
        return result
    }
}
