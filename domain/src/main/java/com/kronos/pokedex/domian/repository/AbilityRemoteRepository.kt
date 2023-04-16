package com.kronos.pokedex.domian.repository

import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.ResponseList
import com.kronos.pokedex.domian.model.ability.AbilityInfo


interface AbilityRemoteRepository {
    suspend fun list(limit:Int,offset:Int): ResponseList<NamedResourceApi>

    suspend fun getAbility(abilityId: Int): AbilityInfo

    suspend fun getAbility(abilityName: String): AbilityInfo
}
