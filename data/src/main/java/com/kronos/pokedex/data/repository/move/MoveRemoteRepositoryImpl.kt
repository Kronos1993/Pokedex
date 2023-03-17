package com.kronos.pokedex.data.repository.move

import com.kronos.pokedex.data.data_source.move.MoveRemoteDataSource
import com.kronos.pokedex.domian.model.move.MoveList
import com.kronos.pokedex.domian.repository.MoveRemoteRepository
import javax.inject.Inject

class MoveRemoteRepositoryImpl@Inject constructor(
    private val moveRemoteDataSource: MoveRemoteDataSource
) : MoveRemoteRepository {

    override suspend fun list(): List<MoveList> {
        return moveRemoteDataSource.listMove();
    }

}