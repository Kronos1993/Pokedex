package com.kronos.pokedex.data.repository.ability

import com.kronos.pokedex.data.data_source.ability.AbilityRemoteDataSource
import com.kronos.pokedex.data.data_source.move.MoveRemoteDataSource
import com.kronos.pokedex.data.data_source.pokemon.PokemonRemoteDataSource
import com.kronos.pokedex.domian.model.ability.Ability
import com.kronos.pokedex.domian.model.move.Move
import com.kronos.pokedex.domian.model.pokemon.PokemonInfo
import com.kronos.pokedex.domian.model.pokemon.PokemonList
import com.kronos.pokedex.domian.repository.AbilityRemoteRepository
import com.kronos.pokedex.domian.repository.MoveRemoteRepository
import com.kronos.pokedex.domian.repository.PokemonRemoteRepository
import javax.inject.Inject

class AbilityRemoteRepositoryImpl@Inject constructor(
    private val abilityRemoteDataSource: AbilityRemoteDataSource
) : AbilityRemoteRepository {

    override suspend fun list(): List<Ability> {
        return abilityRemoteDataSource.listAbility();
    }

}