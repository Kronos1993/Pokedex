package com.kronos.pokedex.data.remote.nature

import com.kronos.pokedex.data.data_source.nature.NatureRemoteDataSource
import com.kronos.pokedex.data.remote.nature.api.NatureApi
import com.kronos.pokedex.data.remote.nature.mapper.toNatureDetail
import com.kronos.pokedex.data.remote.response_list.mapper.toNamedResource
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.ResponseList
import com.kronos.pokedex.domian.model.nature.NatureDetail
import javax.inject.Inject

class NatureRemoteDataSourceImpl @Inject constructor(
    private val natureApi: NatureApi,
) : NatureRemoteDataSource {

    override suspend fun listNature(limit: Int, offset: Int): ResponseList<NamedResourceApi> {
        val result: ResponseList<NamedResourceApi> =
            try {
                natureApi.list(limit, offset).execute().let {
                    if (it.isSuccessful && it.body() != null) {
                        val response = it.body()!!
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
        return result
    }

    override suspend fun getNature(natureId: Int): NatureDetail {
        val result: NatureDetail =
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
        return result
    }

    override suspend fun getNature(nature: String): NatureDetail {
        val result: NatureDetail =
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
        return result
    }

}
