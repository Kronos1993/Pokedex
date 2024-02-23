package com.kronos.pokedex.data.remote.evolution_chain

import com.kronos.pokedex.data.data_source.evolution_chain.EvolutionChainRemoteDataSource
import com.kronos.pokedex.data.remote.evolution_chain.api.EvolutionChainApi
import com.kronos.pokedex.data.remote.evolution_chain.mapper.toEvolutionChain
import com.kronos.pokedex.data.remote.response_list.mapper.toNamedResource
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.ResponseList
import com.kronos.pokedex.domian.model.evolution_chain.EvolutionChain
import javax.inject.Inject

class EvolutionChainRemoteDataSourceImpl @Inject constructor(
    private val evolutionChainApi: EvolutionChainApi,
) : EvolutionChainRemoteDataSource {

    override suspend fun list(limit: Int, offset: Int): ResponseList<NamedResourceApi> {
        val result: ResponseList<NamedResourceApi> =
            try {
                evolutionChainApi.list(limit, offset).execute().let {
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

    override suspend fun getEvolutionChain(chainId: Int): EvolutionChain {
        val result: EvolutionChain =
            try {
                evolutionChainApi.getEvolutionChain(chainId).execute().let {
                    if (it.isSuccessful && it.body() != null) {
                        it.body()!!.toEvolutionChain()
                    } else {
                        EvolutionChain()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                EvolutionChain()
            }
        return result
    }


}
