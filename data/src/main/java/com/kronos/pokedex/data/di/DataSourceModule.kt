package com.kronos.pokedex.data.di

import com.kronos.pokedex.data.data_source.ability.AbilityRemoteDataSource
import com.kronos.pokedex.data.data_source.move.MoveRemoteDataSource
import com.kronos.pokedex.data.data_source.pokemon.PokemonRemoteDataSource
import com.kronos.pokedex.data.data_source.specie.SpecieRemoteDataSource
import com.kronos.pokedex.data.remote.ability.AbilityRemoteDatasourceImpl
import com.kronos.pokedex.data.remote.move.MoveRemoteDatasourceImpl
import com.kronos.pokedex.data.remote.pokemon.PokemonRemoteDatasourceImpl
import com.kronos.pokedex.data.remote.specie.SpecieRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Binds
    abstract fun providePokemonRemoteDataSource(implPokemonRemote: PokemonRemoteDatasourceImpl): PokemonRemoteDataSource

    @Binds
    abstract fun provideAbilityRemoteDataSource(implAbilityRemote: AbilityRemoteDatasourceImpl): AbilityRemoteDataSource

    @Binds
    abstract fun provideMoveRemoteDatasource(implMoveRemote: MoveRemoteDatasourceImpl): MoveRemoteDataSource

    @Binds
    abstract fun provideSpecieRemoteDataSource(implMoveRemote: SpecieRemoteDataSourceImpl): SpecieRemoteDataSource

}
