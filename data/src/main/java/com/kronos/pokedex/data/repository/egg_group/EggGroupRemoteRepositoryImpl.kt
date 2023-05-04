package com.kronos.pokedex.data.repository.egg_group

import com.kronos.pokedex.data.data_source.ability.AbilityRemoteDataSource
import com.kronos.pokedex.data.data_source.egg_group.EggGroupRemoteDataSource
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.ResponseList
import com.kronos.pokedex.domian.model.ability.AbilityInfo
import com.kronos.pokedex.domian.model.egg_group.EggGroupInfo
import com.kronos.pokedex.domian.repository.AbilityRemoteRepository
import com.kronos.pokedex.domian.repository.EggGroupRemoteRepository
import javax.inject.Inject

class EggGroupRemoteRepositoryImpl@Inject constructor(
    private val eggGroupRemoteDataSource: EggGroupRemoteDataSource
) : EggGroupRemoteRepository {
    override suspend fun list(limit: Int, offset: Int): ResponseList<NamedResourceApi> {
        return eggGroupRemoteDataSource.listEggGroup(limit, offset)
    }

    override suspend fun getEggGroup(eggGroupId: Int): EggGroupInfo {
        return eggGroupRemoteDataSource.getEggGroup(eggGroupId)
    }

    override suspend fun getEggGroup(eggGroupName: String): EggGroupInfo {
        return eggGroupRemoteDataSource.getEggGroup(eggGroupName)
    }

}