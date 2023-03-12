package com.kronos.pokedex.data.remote.pokemon

import android.util.Log
import com.kronos.pokedex.data.data_source.pokemon.PokemonRemoteDataSource
import com.kronos.pokedex.data.remote.pokemon.api.PokemonApi
import com.kronos.pokedex.data.remote.pokemon.dto.PokemonInfoDto
import com.kronos.pokedex.data.remote.pokemon.dto.PokemonListDto
import com.kronos.pokedex.data.remote.pokemon.mapper.toPokemonInfo
import com.kronos.pokedex.data.remote.pokemon.mapper.toPokemonList
import com.kronos.pokedex.data.remote.response_list.ResponseListDto
import com.kronos.pokedex.data.remote.specie.dto.SpecieInfoDto
import com.kronos.pokedex.domian.model.ResponseList
import com.kronos.pokedex.domian.model.pokemon.PokemonInfo
import com.kronos.pokedex.domian.model.pokemon.PokemonList
import com.kronos.pokedex.domian.model.specie.SpecieInfo
import com.kronos.pokedex.domian.repository.SpecieRemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import retrofit2.Callback
import javax.inject.Inject

class PokemonRemoteDatasourceImpl @Inject constructor(
    private val pokemonApi: PokemonApi,
    private val specieRemoteRepository: SpecieRemoteRepository,
) : PokemonRemoteDataSource {

    override suspend fun listPokemon(limit:Int, offset:Int): ResponseList<PokemonList> {
        var result: ResponseList<PokemonList> =
            try{
                pokemonApi.list(limit,offset).execute().let {
                    if (it.isSuccessful && it.body() != null) {
                        var response = it.body()!!
                        ResponseList(response.count,response.next,response.results.map { it.toPokemonList() })
                    } else {
                        ResponseList()
                    }
                }
            }catch (e:Exception){
                e.printStackTrace()
                ResponseList()
            }
        Log.e(PokemonRemoteDatasourceImpl::javaClass.name, "pokemon list: ${result.results}")
        return result
    }

    override fun listPokemon(limit: Int,offset: Int, callback: Any) {
        pokemonApi.list(limit, offset).enqueue(callback as Callback<ResponseListDto<PokemonListDto>>)
    }

    override suspend fun getPokemonInfo(pokemonId: Int): PokemonInfo {
        var result: PokemonInfo =
            try{
                pokemonApi.getPokemonInfo(pokemonId).execute().let {
                    if (it.isSuccessful && it.body() != null) {
                        it.body()!!.toPokemonInfo()
                    } else {
                        PokemonInfo()
                    }
                }
            }catch (e:Exception){
                e.printStackTrace()
                PokemonInfo()
            }
        Log.e(PokemonRemoteDatasourceImpl::javaClass.name, "pokemon list: $result")
        return result
    }

    override suspend fun getPokemonInfo(pokemonName: String): PokemonInfo {
        var result: PokemonInfo =
            try{
                pokemonApi.getPokemonInfo(pokemonName).execute().let {
                    if (it.isSuccessful && it.body() != null) {
                        it.body()!!.toPokemonInfo()
                    } else {
                        PokemonInfo()
                    }
                }
            }catch (e:Exception){
                e.printStackTrace()
                PokemonInfo()
            }
        Log.e(PokemonRemoteDatasourceImpl::javaClass.name, "pokemon list: $result")
        return result
    }

    override fun getPokemonInfo(pokemonId: Int, callback: Any) {
        pokemonApi.getPokemonInfo(pokemonId).enqueue(callback as Callback<PokemonInfoDto>)
    }

    override fun getPokemonInfo(pokemonName: String, callback: Any) {
        pokemonApi.getPokemonInfo(pokemonName).enqueue(callback as Callback<PokemonInfoDto>)
    }

}
