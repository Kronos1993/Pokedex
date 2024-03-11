package com.kronos.pokedex.data.repository.pokemon

import com.kronos.pokedex.data.data_source.pokemon.PokemonRemoteDataSource
import com.kronos.pokedex.data.remote.pokemon.dto.PokemonInfoDto
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.ResponseList
import com.kronos.pokedex.domian.model.pokemon.Encounter
import com.kronos.pokedex.domian.model.pokemon.PokemonInfo
import com.kronos.pokedex.domian.repository.PokemonRemoteRepository
import retrofit2.Callback
import javax.inject.Inject

class PokemonRemoteRepositoryImpl@Inject constructor(
    private val pokemonRemoteDataSource: PokemonRemoteDataSource
) : PokemonRemoteRepository {
    override suspend fun listPokemon(limit:Int,offset:Int): ResponseList<NamedResourceApi> {
        return pokemonRemoteDataSource.listPokemon(limit,offset)
    }

    override fun getPokemonInfo(pokemonId: Int, callback: Any) {
        return pokemonRemoteDataSource.getPokemonInfo(pokemonId, callback as Callback<PokemonInfoDto>)
    }

    override suspend fun getPokemonInfo(pokemonId: Int): PokemonInfo {
        return pokemonRemoteDataSource.getPokemonInfo(pokemonId)
    }

    override suspend fun getPokemonInfo(pokemonName: String): PokemonInfo {
        return pokemonRemoteDataSource.getPokemonInfo(pokemonName)
    }

    override fun getPokemonInfo(pokemonName: String, callback: Any) {
        return pokemonRemoteDataSource.getPokemonInfo(pokemonName,callback as Callback<PokemonInfoDto>)
    }

    override suspend fun getPokemonEncountersInfo(pokemonId: Int): List<Encounter> {
        return pokemonRemoteDataSource.getPokemonEncountersInfo(pokemonId)
    }

    override suspend fun getPokemonEncountersInfo(pokemon: String): List<Encounter> {
        return pokemonRemoteDataSource.getPokemonEncountersInfo(pokemon)
    }
}