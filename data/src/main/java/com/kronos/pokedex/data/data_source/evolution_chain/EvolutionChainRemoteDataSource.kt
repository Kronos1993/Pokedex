package com.kronos.pokedex.data.data_source.evolution_chain
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.ResponseList
import com.kronos.pokedex.domian.model.evolution_chain.EvolutionChain


interface EvolutionChainRemoteDataSource {
    suspend fun list(limit:Int = 20,offset:Int = 0): ResponseList<NamedResourceApi>

    suspend fun getEvolutionChain(chainId: Int = 1): EvolutionChain

}