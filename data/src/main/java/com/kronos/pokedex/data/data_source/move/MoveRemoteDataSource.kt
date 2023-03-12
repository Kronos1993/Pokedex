package com.kronos.pokedex.data.data_source.move

import com.kronos.pokedex.domian.model.move.Move

interface MoveRemoteDataSource {
    suspend fun listMove(): List<Move>
}