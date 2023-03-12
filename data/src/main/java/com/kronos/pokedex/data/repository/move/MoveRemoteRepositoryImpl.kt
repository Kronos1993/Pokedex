package com.kronos.pokedex.data.repository.move

import com.kronos.pokedex.data.data_source.move.MoveRemoteDataSource
import com.kronos.pokedex.data.data_source.pokemon.PokemonRemoteDataSource
import com.kronos.pokedex.domian.model.move.Move
import com.kronos.pokedex.domian.model.pokemon.PokemonInfo
import com.kronos.pokedex.domian.model.pokemon.PokemonList
import com.kronos.pokedex.domian.repository.MoveRemoteRepository
import com.kronos.pokedex.domian.repository.PokemonRemoteRepository
import javax.inject.Inject

class MoveRemoteRepositoryImpl@Inject constructor(
    private val moveRemoteDataSource: MoveRemoteDataSource
) : MoveRemoteRepository {

    override suspend fun list(): List<Move> {
        return moveRemoteDataSource.listMove();
    }

}