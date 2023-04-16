package com.kronos.pokedex.data.repository.evolution_chain

import com.kronos.pokedex.data.data_source.evolution_chain.EvolutionChainRemoteDataSource
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.ResponseList
import com.kronos.pokedex.domian.model.evolution_chain.EvolutionChain
import com.kronos.pokedex.domian.repository.EvolutionChainRemoteRepository
import javax.inject.Inject

class EvolutionChainRemoteRepositoryImpl@Inject constructor(
    private val evolutionChainRemoteDataSource: EvolutionChainRemoteDataSource
) : EvolutionChainRemoteRepository {
    override suspend fun list(limit: Int, offset: Int): ResponseList<NamedResourceApi> {
        return evolutionChainRemoteDataSource.list(limit, offset)
    }

    override suspend fun getEvolutionChain(chainId: Int): EvolutionChain {
        return evolutionChainRemoteDataSource.getEvolutionChain(chainId)
    }

}