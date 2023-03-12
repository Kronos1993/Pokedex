package com.kronos.pokedex.data.data_source.ability

import com.kronos.pokedex.domian.model.ability.Ability

interface AbilityRemoteDataSource {
    suspend fun listAbility(): List<Ability>
}