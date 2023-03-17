package com.kronos.pokedex.domian.repository

import com.kronos.pokedex.domian.model.move.MoveList


interface MoveRemoteRepository {
    suspend fun list(): List<MoveList>
}
