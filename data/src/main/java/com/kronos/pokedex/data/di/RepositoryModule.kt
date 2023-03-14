package com.kronos.pokedex.data.di

import com.kronos.pokedex.data.repository.ability.AbilityRemoteRepositoryImpl
import com.kronos.pokedex.data.repository.move.MoveRemoteRepositoryImpl
import com.kronos.pokedex.data.repository.pokedex.PokedexRemoteRepositoryImpl
import com.kronos.pokedex.data.repository.pokemon.PokemonRemoteRepositoryImpl
import com.kronos.pokedex.data.repository.specie.SpecieRemoteRepositoryImpl
import com.kronos.pokedex.domian.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun providePokedexRemoteRepository(impl: PokedexRemoteRepositoryImpl): PokedexRemoteRepository

    @Binds
    abstract fun providePokemonRemoteRepository(impl: PokemonRemoteRepositoryImpl): PokemonRemoteRepository

    @Binds
    abstract fun provideAbilityRemoteRepository(impl: AbilityRemoteRepositoryImpl): AbilityRemoteRepository

    @Binds
    abstract fun provideMoveRemoteRepository(impl: MoveRemoteRepositoryImpl): MoveRemoteRepository

    @Binds
    abstract fun provideSpecieRemoteRepository(impl: SpecieRemoteRepositoryImpl): SpecieRemoteRepository

}
