package com.kronos.pokedex.data.di

import com.kronos.pokedex.data.data_source.ability.AbilityRemoteDataSource
import com.kronos.pokedex.data.data_source.berry.BerryRemoteDataSource
import com.kronos.pokedex.data.data_source.egg_group.EggGroupRemoteDataSource
import com.kronos.pokedex.data.data_source.evolution_chain.EvolutionChainRemoteDataSource
import com.kronos.pokedex.data.data_source.item.ItemRemoteDataSource
import com.kronos.pokedex.data.data_source.move.MoveRemoteDataSource
import com.kronos.pokedex.data.data_source.nature.NatureRemoteDataSource
import com.kronos.pokedex.data.data_source.pokedex.PokedexRemoteDataSource
import com.kronos.pokedex.data.data_source.pokemon.PokemonRemoteDataSource
import com.kronos.pokedex.data.data_source.specie.SpecieRemoteDataSource
import com.kronos.pokedex.data.data_source.type.TypeRemoteDataSource
import com.kronos.pokedex.data.remote.ability.AbilityRemoteDataSourceImpl
import com.kronos.pokedex.data.remote.berry.BerryRemoteDataSourceImpl
import com.kronos.pokedex.data.remote.egg_group.EggGroupRemoteDataSourceImpl
import com.kronos.pokedex.data.remote.evolution_chain.EvolutionChainRemoteDataSourceImpl
import com.kronos.pokedex.data.remote.item.ItemRemoteDataSourceImpl
import com.kronos.pokedex.data.remote.move.MoveRemoteDataSourceImpl
import com.kronos.pokedex.data.remote.nature.NatureRemoteDataSourceImpl
import com.kronos.pokedex.data.remote.pokedex.PokedexRemoteDataSourceImpl
import com.kronos.pokedex.data.remote.pokemon.PokemonRemoteDatasourceImpl
import com.kronos.pokedex.data.remote.specie.SpecieRemoteDataSourceImpl
import com.kronos.pokedex.data.remote.type.TypeRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun providePokedexRemoteDataSource(implPokedexRemote: PokedexRemoteDataSourceImpl): PokedexRemoteDataSource

    @Binds
    abstract fun providePokemonRemoteDataSource(implPokemonRemote: PokemonRemoteDatasourceImpl): PokemonRemoteDataSource

    @Binds
    abstract fun provideAbilityRemoteDataSource(implAbilityRemote: AbilityRemoteDataSourceImpl): AbilityRemoteDataSource

    @Binds
    abstract fun provideMoveRemoteDatasource(implMoveRemote: MoveRemoteDataSourceImpl): MoveRemoteDataSource

    @Binds
    abstract fun provideSpecieRemoteDataSource(implSpecieRemote: SpecieRemoteDataSourceImpl): SpecieRemoteDataSource

    @Binds
    abstract fun provideEvolutionChainRemoteDataSource(implEvoChainRemote: EvolutionChainRemoteDataSourceImpl): EvolutionChainRemoteDataSource

    @Binds
    abstract fun provideNatureRemoteDataSource(implNatureRemote: NatureRemoteDataSourceImpl): NatureRemoteDataSource

    @Binds
    abstract fun provideBerryRemoteDataSource(implBerryRemote: BerryRemoteDataSourceImpl): BerryRemoteDataSource

    @Binds
    abstract fun provideItemRemoteDataSource(implItemRemote: ItemRemoteDataSourceImpl): ItemRemoteDataSource

    @Binds
    abstract fun provideEggGroupRemoteDataSource(implEggGroupRemote: EggGroupRemoteDataSourceImpl): EggGroupRemoteDataSource

    @Binds
    abstract fun provideTypeRemoteDataSource(implTypeRemote: TypeRemoteDataSourceImpl): TypeRemoteDataSource

}
