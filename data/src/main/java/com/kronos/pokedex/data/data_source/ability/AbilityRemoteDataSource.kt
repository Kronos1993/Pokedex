package com.kronos.pokedex.data.data_source.ability

import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.ResponseList
import com.kronos.pokedex.domian.model.ability.AbilityInfo

interface AbilityRemoteDataSource {
    suspend fun listAbility(limit:Int = 20,offset:Int = 0): ResponseList<NamedResourceApi>

    suspend fun getAbility(abilityId: Int = 1): AbilityInfo

    suspend fun getAbility(ability: String): AbilityInfo
}