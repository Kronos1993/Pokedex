package com.kronos.pokedex.domian.repository

import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.ResponseList
import com.kronos.pokedex.domian.model.egg_group.EggGroupInfo


interface EggGroupRemoteRepository {
    suspend fun list(limit: Int, offset: Int): ResponseList<NamedResourceApi>

    suspend fun getEggGroup(eggGroupId: Int): EggGroupInfo

    suspend fun getEggGroup(eggGroupName: String): EggGroupInfo
}
