package com.kronos.pokedex.data.data_source.egg_group

import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.ResponseList
import com.kronos.pokedex.domian.model.ability.AbilityInfo
import com.kronos.pokedex.domian.model.egg_group.EggGroupInfo

interface EggGroupRemoteDataSource {
    suspend fun listEggGroup(limit:Int = 20,offset:Int = 0): ResponseList<NamedResourceApi>

    suspend fun getEggGroup(eggGroupId: Int = 1): EggGroupInfo

    suspend fun getEggGroup(eggGroup: String): EggGroupInfo
}