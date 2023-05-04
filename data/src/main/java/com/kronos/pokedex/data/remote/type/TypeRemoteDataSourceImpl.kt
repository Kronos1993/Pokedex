package com.kronos.pokedex.data.remote.type

import android.util.Log
import com.kronos.pokedex.data.data_source.ability.AbilityRemoteDataSource
import com.kronos.pokedex.data.data_source.egg_group.EggGroupRemoteDataSource
import com.kronos.pokedex.data.data_source.type.TypeRemoteDataSource
import com.kronos.pokedex.data.remote.ability.api.AbilityApi
import com.kronos.pokedex.data.remote.ability.mapper.toAbilityInfo
import com.kronos.pokedex.data.remote.egg_group.api.EggGroupApi
import com.kronos.pokedex.data.remote.egg_group.mapper.toEggGroupInfo
import com.kronos.pokedex.data.remote.pokedex.PokedexRemoteDataSourceImpl
import com.kronos.pokedex.data.remote.response_list.mapper.toNamedResource
import com.kronos.pokedex.data.remote.type.api.TypeApi
import com.kronos.pokedex.data.remote.type.mapper.toTypeInfo
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.ResponseList
import com.kronos.pokedex.domian.model.ability.AbilityInfo
import com.kronos.pokedex.domian.model.egg_group.EggGroupInfo
import com.kronos.pokedex.domian.model.type.TypeInfo
import javax.inject.Inject

class TypeRemoteDataSourceImpl @Inject constructor(
    private val typeApi: TypeApi,
) : TypeRemoteDataSource {

    override suspend fun listType(limit: Int, offset: Int): ResponseList<NamedResourceApi> {
        var result: ResponseList<NamedResourceApi> =
            try{
                typeApi.list(limit,offset).execute().let {
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
        Log.e(TypeRemoteDataSourceImpl::javaClass.name, "type list: $result")
        return result
    }

    override suspend fun getTypeInfo(typeId: Int): TypeInfo {
        var result: TypeInfo =
            try {
                typeApi.getTypeInfo(typeId).execute().let {
                    if (it.isSuccessful && it.body() != null) {
                        it.body()!!.toTypeInfo()
                    } else {
                        TypeInfo()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                TypeInfo()
            }
        Log.e(TypeRemoteDataSourceImpl::javaClass.name, "type: $result")
        return result
    }

    override suspend fun getTypeInfo(type: String): TypeInfo {
        var result: TypeInfo =
            try {
                typeApi.getTypeInfo(type).execute().let {
                    if (it.isSuccessful && it.body() != null) {
                        it.body()!!.toTypeInfo()
                    } else {
                        TypeInfo()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                TypeInfo()
            }
        Log.e(TypeRemoteDataSourceImpl::javaClass.name, "type: $result")
        return result
    }
}
