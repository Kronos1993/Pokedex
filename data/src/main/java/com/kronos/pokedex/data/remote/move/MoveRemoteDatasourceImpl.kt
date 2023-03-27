package com.kronos.pokedex.data.remote.move

import android.util.Log
import com.kronos.pokedex.data.data_source.move.MoveRemoteDataSource
import com.kronos.pokedex.data.remote.move.api.MoveApi
import com.kronos.pokedex.data.remote.move.mapper.toMoveInfo
import com.kronos.pokedex.data.remote.move.mapper.toMoveList
import com.kronos.pokedex.data.remote.pokedex.PokedexRemoteDataSourceImpl
import com.kronos.pokedex.data.remote.response_list.mapper.toNamedResource
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.ResponseList
import com.kronos.pokedex.domian.model.move.MoveInfo
import com.kronos.pokedex.domian.model.move.MoveList
import javax.inject.Inject

class MoveRemoteDatasourceImpl @Inject constructor(
    private val moveApi: MoveApi,
) : MoveRemoteDataSource {

    override suspend fun listMove(limit: Int, offset: Int): ResponseList<NamedResourceApi> {
        var result: ResponseList<NamedResourceApi> =
            try {
                moveApi.list(limit, offset).execute().let {
                    if (it.isSuccessful && it.body() != null) {
                        var response = it.body()!!
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
        Log.e(MoveRemoteDatasourceImpl::javaClass.name, "move list: $result")
        return result
    }

    override suspend fun getMove(moveId: Int): MoveInfo {
        var result: MoveInfo =
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
        Log.e(PokedexRemoteDataSourceImpl::javaClass.name, "move: $result")
        return result
    }

    override suspend fun getMove(move: String): MoveInfo {
        var result: MoveInfo =
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
        Log.e(PokedexRemoteDataSourceImpl::javaClass.name, "move: $result")
        return result
    }

}
