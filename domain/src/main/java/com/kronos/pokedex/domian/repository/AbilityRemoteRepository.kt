package com.kronos.pokedex.domian.repository

import com.kronos.pokedex.domian.model.ability.Ability


interface AbilityRemoteRepository {
    suspend fun list(): List<Ability>
}
