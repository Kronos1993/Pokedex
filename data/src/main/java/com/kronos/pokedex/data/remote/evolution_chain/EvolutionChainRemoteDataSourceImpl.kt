package com.kronos.pokedex.data.remote.evolution_chain

import android.util.Log
import com.kronos.pokedex.data.data_source.evolution_chain.EvolutionChainRemoteDataSource
import com.kronos.pokedex.data.data_source.pokedex.PokedexRemoteDataSource
import com.kronos.pokedex.data.remote.evolution_chain.api.EvolutionChainApi
import com.kronos.pokedex.data.remote.evolution_chain.dto.EvolutionChainDto
import com.kronos.pokedex.data.remote.evolution_chain.mapper.toChainLink
import com.kronos.pokedex.data.remote.evolution_chain.mapper.toEvolutionChain
import com.kronos.pokedex.data.remote.pokedex.api.PokedexApi
import com.kronos.pokedex.data.remote.pokedex.mapper.toPokedex
import com.kronos.pokedex.data.remote.response_list.mapper.toNamedResource
import com.kronos.pokedex.domian.model.ResponseList
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.evolution_chain.ChainLink
import com.kronos.pokedex.domian.model.evolution_chain.EvolutionChain
import com.kronos.pokedex.domian.model.pokedex.Pokedex
import javax.inject.Inject

class EvolutionChainRemoteDataSourceImpl @Inject constructor(
    private val evolutionChainApi: EvolutionChainApi,
) : EvolutionChainRemoteDataSource {

    override suspend fun list(limit: Int, offset: Int): ResponseList<NamedResourceApi> {
        var result: ResponseList<NamedResourceApi> =
            try {
                evolutionChainApi.list(limit, offset).execute().let {
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
        Log.e(EvolutionChainRemoteDataSourceImpl::javaClass.name, "pokemon list: ${result.results}")
        return result
    }

    override suspend fun getEvolutionChain(chainId: Int): EvolutionChain {
        var result: EvolutionChain =
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
        Log.e(EvolutionChainRemoteDataSourceImpl::javaClass.name, "pokemon list: $result")
        return result
    }


}
