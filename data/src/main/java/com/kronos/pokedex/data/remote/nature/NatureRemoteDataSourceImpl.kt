package com.kronos.pokedex.data.remote.nature

import android.util.Log
import com.kronos.pokedex.data.data_source.nature.NatureRemoteDataSource
import com.kronos.pokedex.data.remote.nature.api.NatureApi
import com.kronos.pokedex.data.remote.nature.mapper.toNatureDetail
import com.kronos.pokedex.data.remote.pokedex.PokedexRemoteDataSourceImpl
import com.kronos.pokedex.data.remote.response_list.mapper.toNamedResource
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.ResponseList
import com.kronos.pokedex.domian.model.nature.NatureDetail
import javax.inject.Inject

class NatureRemoteDataSourceImpl @Inject constructor(
    private val natureApi: NatureApi,
) : NatureRemoteDataSource {

    override suspend fun listNature(limit: Int, offset: Int): ResponseList<NamedResourceApi> {
        var result: ResponseList<NamedResourceApi> =
            try {
                natureApi.list(limit, offset).execute().let {
                    if (it.isSuccessful && it.body() != null) {
                        var response = it.body()!!
                        ResponseList(response.count, response.next, response.results.map {
                            it.toNamedResource()
                        })
                    } else {
                        ResponseList()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                ResponseList()
            }
        Log.e(NatureRemoteDataSourceImpl::javaClass.name, "nature list: $result")
        return result
    }

    override suspend fun getNature(natureId: Int): NatureDetail {
        var result: NatureDetail =
            try {
                natureApi.getNature(natureId).execute().let {
                    if (it.isSuccessful && it.body() != null) {
                        it.body()!!.toNatureDetail()
                    } else {
                        NatureDetail()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                NatureDetail()
            }
        Log.e(PokedexRemoteDataSourceImpl::javaClass.name, "nature: $result")
        return result
    }

    override suspend fun getNature(nature: String): NatureDetail {
        var result: NatureDetail =
            try {
                natureApi.getNature(nature).execute().let {
                    if (it.isSuccessful && it.body() != null) {
                        it.body()!!.toNatureDetail()
                    } else {
                        NatureDetail()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                NatureDetail()
            }
        Log.e(PokedexRemoteDataSourceImpl::javaClass.name, "nature: $result")
        return result
    }

}
