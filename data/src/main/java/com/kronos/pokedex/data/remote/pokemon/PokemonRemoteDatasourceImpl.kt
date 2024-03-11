package com.kronos.pokedex.data.remote.pokemon

import com.kronos.pokedex.data.data_source.pokemon.PokemonRemoteDataSource
import com.kronos.pokedex.data.remote.pokemon.api.PokemonApi
import com.kronos.pokedex.data.remote.pokemon.dto.PokemonInfoDto
import com.kronos.pokedex.data.remote.pokemon.mapper.toEncounter
import com.kronos.pokedex.data.remote.pokemon.mapper.toPokemonInfo
import com.kronos.pokedex.data.remote.response_list.NamedResourceApiDto
import com.kronos.pokedex.data.remote.response_list.ResponseListDto
import com.kronos.pokedex.data.remote.response_list.mapper.toNamedResource
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.ResponseList
import com.kronos.pokedex.domian.model.pokemon.Encounter
import com.kronos.pokedex.domian.model.pokemon.PokemonInfo
import com.kronos.pokedex.domian.repository.SpecieRemoteRepository
import retrofit2.Callback
import javax.inject.Inject

class PokemonRemoteDatasourceImpl @Inject constructor(
    private val pokemonApi: PokemonApi,
    private val specieRemoteRepository: SpecieRemoteRepository,
) : PokemonRemoteDataSource {

    override suspend fun listPokemon(limit:Int, offset:Int): ResponseList<NamedResourceApi> {
        val result: ResponseList<NamedResourceApi> =
            try{
                pokemonApi.list(limit,offset).execute().let {
                    if (it.isSuccessful && it.body() != null) {
                        val response = it.body()!!
                        ResponseList(response.count,response.next,response.results.map { it.toNamedResource() })
                    } else {
                        ResponseList()
                    }
                }
            }catch (e:Exception){
                e.printStackTrace()
                ResponseList()
            }
        return result
    }

    override fun listPokemon(limit: Int,offset: Int, callback: Callback<ResponseListDto<NamedResourceApiDto>>) {
        pokemonApi.list(limit, offset).enqueue(callback)
    }

    override suspend fun getPokemonInfo(pokemonId: Int): PokemonInfo {
        val result: PokemonInfo =
            try{
                pokemonApi.getPokemonInfo(pokemonId).execute().let {
                    if (it.isSuccessful && it.body() != null) {
                        it.body()!!.toPokemonInfo(specieRemoteRepository.getSpecieInfo(it.body()!!.specie.name))
                    } else {
                        PokemonInfo()
                    }
                }
            }catch (e:Exception){
                e.printStackTrace()
                PokemonInfo()
            }
        return result
    }

    override suspend fun getPokemonInfo(pokemonName: String): PokemonInfo {
        val result: PokemonInfo =
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
        return result
    }

    override fun getPokemonInfo(pokemonId: Int, callback: Callback<PokemonInfoDto>) {
        pokemonApi.getPokemonInfo(pokemonId).enqueue(callback)
    }

    override fun getPokemonInfo(pokemonName: String, callback: Callback<PokemonInfoDto>) {
        pokemonApi.getPokemonInfo(pokemonName).enqueue(callback)
    }

    override suspend fun getPokemonEncountersInfo(pokemonId: Int): List<Encounter> {
        val result: List<Encounter> =
            try{
                pokemonApi.getPokemonEncountersInfo(pokemonId).execute().let { response ->
                    if (response.isSuccessful && response.body() != null) {
                        response.body()!!.map {
                            it.toEncounter()
                        }
                    } else {
                        listOf()
                    }
                }
            }catch (e:Exception){
                e.printStackTrace()
                listOf()
            }
        return result
    }

    override suspend fun getPokemonEncountersInfo(pokemon: String): List<Encounter> {
        val result: List<Encounter> =
            try{
                pokemonApi.getPokemonEncountersInfo(pokemon).execute().let { response ->
                    if (response.isSuccessful && response.body() != null) {
                        response.body()!!.map {
                            it.toEncounter()
                        }
                    } else {
                        listOf()
                    }
                }
            }catch (e:Exception){
                e.printStackTrace()
                listOf()
            }
        return result
    }

}
