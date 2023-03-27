package com.kronos.pokedex.data.repository.move

import com.kronos.pokedex.data.data_source.move.MoveRemoteDataSource
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.ResponseList
import com.kronos.pokedex.domian.model.move.MoveInfo
import com.kronos.pokedex.domian.model.move.MoveList
import com.kronos.pokedex.domian.repository.MoveRemoteRepository
import javax.inject.Inject

class MoveRemoteRepositoryImpl@Inject constructor(
    private val moveRemoteDataSource: MoveRemoteDataSource
) : MoveRemoteRepository {

    override suspend fun list(limit: Int, offset: Int): ResponseList<NamedResourceApi> {
        return moveRemoteDataSource.listMove(limit, offset)
    }

    override suspend fun getMove(moveId: Int): MoveInfo {
        return moveRemoteDataSource.getMove(moveId)
    }

    override suspend fun getMove(moveName: String): MoveInfo {
        return moveRemoteDataSource.getMove(moveName)
    }

}