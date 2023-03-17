package com.kronos.pokedex.data.remote.move

import android.util.Log
import com.kronos.pokedex.data.data_source.move.MoveRemoteDataSource
import com.kronos.pokedex.data.remote.move.api.MoveApi
import com.kronos.pokedex.data.remote.move.mapper.toMoveList
import com.kronos.pokedex.domian.model.move.MoveList
import javax.inject.Inject

class MoveRemoteDatasourceImpl @Inject constructor(
    private val moveApi: MoveApi,
) : MoveRemoteDataSource {

    override suspend fun listMove(): List<MoveList> {
        var result: List<MoveList> =
            try{
                moveApi.list().execute().let {
                    if (it.isSuccessful && it.body() != null) {
                        it.body()!!.results.map {moveDto->
                            moveDto.toMoveList()
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
