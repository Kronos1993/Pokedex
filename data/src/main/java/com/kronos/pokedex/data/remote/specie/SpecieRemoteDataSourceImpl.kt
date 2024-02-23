package com.kronos.pokedex.data.remote.specie

import com.kronos.pokedex.data.data_source.specie.SpecieRemoteDataSource
import com.kronos.pokedex.data.remote.specie.api.SpecieApi
import com.kronos.pokedex.data.remote.specie.mapper.toSpecieInfo
import com.kronos.pokedex.domian.model.specie.SpecieInfo
import javax.inject.Inject

class SpecieRemoteDataSourceImpl @Inject constructor(
    private val specieApi: SpecieApi,
) : SpecieRemoteDataSource {

    override suspend fun getSpecie(pokemonName: String): SpecieInfo {
        val result: SpecieInfo =
            try{
                specieApi.getSpecieInfo(pokemonName).execute().let {
                    if (it.isSuccessful && it.body() != null) {
                        it.body()!!.toSpecieInfo()
                    } else {
                        SpecieInfo()
                    }
                }
            }catch (e:Exception){
                e.printStackTrace()
                SpecieInfo()
            }
        return result
    }

    override suspend fun getSpecie(pokemonId: Int): SpecieInfo {
        val result: SpecieInfo =
            try{
                specieApi.getSpecieInfo(pokemonId).execute().let {
                    if (it.isSuccessful && it.body() != null) {
                        it.body()!!.toSpecieInfo()
                    } else {
                        SpecieInfo()
                    }
                }
            }catch (e:Exception){
                e.printStackTrace()
                SpecieInfo()
            }
        return result
    }
}
