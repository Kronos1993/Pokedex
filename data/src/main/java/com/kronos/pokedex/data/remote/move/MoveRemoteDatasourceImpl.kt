package com.kronos.pokedex.data.remote.move

import android.util.Log
import com.kronos.pokedex.data.data_source.move.MoveRemoteDataSource
import com.kronos.pokedex.data.remote.move.api.MoveApi
import com.kronos.pokedex.data.remote.move.mapper.toMove
import com.kronos.pokedex.domian.model.move.Move
import javax.inject.Inject

class MoveRemoteDatasourceImpl @Inject constructor(
    private val moveApi: MoveApi,
) : MoveRemoteDataSource {

    override suspend fun listMove(): List<Move> {
        var result: List<Move> =
            try{
                moveApi.list().execute().let {
                    if (it.isSuccessful && it.body() != null) {
                        it.body()!!.results.map {moveDto->
                            moveDto.toMove()
                        }
                    } else {
                        listOf()
                    }
                }
            }catch (e:Exception){
                e.printStackTrace()
                listOf()
            }
        Log.e(MoveRemoteDatasourceImpl::javaClass.name, "move list: $result")
        return result
    }
}
