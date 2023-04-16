package com.kronos.pokedex.data.data_source.move

import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.ResponseList
import com.kronos.pokedex.domian.model.ability.AbilityInfo
import com.kronos.pokedex.domian.model.move.MoveInfo
import com.kronos.pokedex.domian.model.move.MoveList

interface MoveRemoteDataSource {
    suspend fun listMove(limit:Int = 20,offset:Int = 0): ResponseList<NamedResourceApi>

    suspend fun getMove(moveId: Int = 1): MoveInfo

    suspend fun getMove(move: String): MoveInfo
}