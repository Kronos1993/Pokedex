package com.kronos.pokedex.domian.repository

import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.ResponseList
import com.kronos.pokedex.domian.model.evolution_chain.ChainLink
import com.kronos.pokedex.domian.model.evolution_chain.EvolutionChain


interface EvolutionChainRemoteRepository {
    suspend fun list(limit:Int,offset:Int): ResponseList<NamedResourceApi>

    suspend fun getEvolutionChain(chainId: Int): EvolutionChain
}
