package com.kronos.pokedex.data.repository.ability

import com.kronos.pokedex.data.data_source.ability.AbilityRemoteDataSource
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.ResponseList
import com.kronos.pokedex.domian.model.ability.AbilityInfo
import com.kronos.pokedex.domian.repository.AbilityRemoteRepository
import javax.inject.Inject

class AbilityRemoteRepositoryImpl@Inject constructor(
    private val abilityRemoteDataSource: AbilityRemoteDataSource
) : AbilityRemoteRepository {
    override suspend fun list(limit: Int, offset: Int): ResponseList<NamedResourceApi> {
        return abilityRemoteDataSource.listAbility(limit, offset)
    }

    override suspend fun getAbility(abilityId: Int): AbilityInfo {
        return abilityRemoteDataSource.getAbility(abilityId)
    }

    override suspend fun getAbility(abilityName: String): AbilityInfo {
        return abilityRemoteDataSource.getAbility(abilityName)
    }

}