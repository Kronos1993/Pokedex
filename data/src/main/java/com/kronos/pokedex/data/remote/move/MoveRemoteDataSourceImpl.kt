package com.kronos.pokedex.data.remote.move

import com.kronos.pokedex.data.data_source.move.MoveRemoteDataSource
import com.kronos.pokedex.data.remote.move.api.MoveApi
import com.kronos.pokedex.data.remote.move.mapper.toMoveInfo
import com.kronos.pokedex.data.remote.response_list.mapper.toNamedResource
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.ResponseList
import com.kronos.pokedex.domian.model.move.MoveInfo
import javax.inject.Inject

class MoveRemoteDataSourceImpl @Inject constructor(
    private val moveApi: MoveApi,
) : MoveRemoteDataSource {

    override suspend fun listMove(limit: Int, offset: Int): ResponseList<NamedResourceApi> {
        val result: ResponseList<NamedResourceApi> =
            try {
                moveApi.list(limit, offset).execute().let {
                    if (it.isSuccessful && it.body() != null) {
                        val response = it.body()!!
                        ResponseList(response.count, response.next, response.results.map {
                            it.toNamedResource()
                        })
                    } else {
                        ResponseList()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                ResponseList()
            }
        return result
    }

    override suspend fun getMove(moveId: Int): MoveInfo {
        val result: MoveInfo =
            try {
                moveApi.getMove(moveId).execute().let {
                    if (it.isSuccessful && it.body() != null) {
                        it.body()!!.toMoveInfo()
                    } else {
                        MoveInfo()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                MoveInfo()
            }
        return result
    }

    override suspend fun getMove(move: String): MoveInfo {
        val result: MoveInfo =
            try {
                moveApi.getMove(move).execute().let {
                    if (it.isSuccessful && it.body() != null) {
                        it.body()!!.toMoveInfo()
                    } else {
                        MoveInfo()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                MoveInfo()
            }
        return result
    }

}
