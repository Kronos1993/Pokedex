package com.kronos.pokedex.data.repository.ability

import com.kronos.pokedex.data.data_source.ability.AbilityRemoteDataSource
import com.kronos.pokedex.domian.model.ability.Ability
import com.kronos.pokedex.domian.repository.AbilityRemoteRepository
import javax.inject.Inject

class AbilityRemoteRepositoryImpl@Inject constructor(
    private val abilityRemoteDataSource: AbilityRemoteDataSource
) : AbilityRemoteRepository {

    override suspend fun list(): List<Ability> {
        return abilityRemoteDataSource.listAbility();
    }

}