package com.kronos.pokedex.data.remote.pokedex

import com.kronos.pokedex.data.data_source.pokedex.PokedexRemoteDataSource
import com.kronos.pokedex.data.remote.pokedex.api.PokedexApi
import com.kronos.pokedex.data.remote.pokedex.mapper.toPokedex
import com.kronos.pokedex.data.remote.response_list.mapper.toNamedResource
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.ResponseList
import com.kronos.pokedex.domian.model.pokedex.Pokedex
import javax.inject.Inject

class PokedexRemoteDataSourceImpl @Inject constructor(
    private val pokedexApi: PokedexApi,
) : PokedexRemoteDataSource {

    override suspend fun list(limit: Int, offset: Int): ResponseList<NamedResourceApi> {
        val result: ResponseList<NamedResourceApi> =
            try {
                pokedexApi.list(limit, offset).execute().let {
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

    override suspend fun getPokedex(pokedexId: Int): Pokedex {
        val result: Pokedex =
            try {
                pokedexApi.getPokedex(pokedexId).execute().let {
                    if (it.isSuccessful && it.body() != null) {
                        it.body()!!.toPokedex()
                    } else {
                        Pokedex()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Pokedex()
            }
        return result
    }

    override suspend fun getPokedex(pokedex: String): Pokedex {
        val result: Pokedex =
            try {
                pokedexApi.getPokedex(pokedex).execute().let {
                    if (it.isSuccessful && it.body() != null) {
                        it.body()!!.toPokedex()
                    } else {
                        Pokedex()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Pokedex()
            }
        return result
    }

}
