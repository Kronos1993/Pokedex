package com.kronos.pokedex.data.remote.type

import com.kronos.pokedex.data.data_source.type.TypeRemoteDataSource
import com.kronos.pokedex.data.remote.response_list.mapper.toNamedResource
import com.kronos.pokedex.data.remote.type.api.TypeApi
import com.kronos.pokedex.data.remote.type.mapper.toTypeInfo
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.ResponseList
import com.kronos.pokedex.domian.model.type.TypeInfo
import javax.inject.Inject

class TypeRemoteDataSourceImpl @Inject constructor(
    private val typeApi: TypeApi,
) : TypeRemoteDataSource {

    override suspend fun listType(limit: Int, offset: Int): ResponseList<NamedResourceApi> {
        val result: ResponseList<NamedResourceApi> =
            try{
                typeApi.list(limit,offset).execute().let {
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

    override suspend fun getTypeInfo(typeId: Int): TypeInfo {
        val result: TypeInfo =
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
        return result
    }

    override suspend fun getTypeInfo(type: String): TypeInfo {
        val result: TypeInfo =
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
        return result
    }
}
