package com.kronos.pokedex.data.di

import com.kronos.pokedex.data.remote.ability.api.AbilityApi
import com.kronos.pokedex.data.remote.berry.api.BerryApi
import com.kronos.pokedex.data.remote.egg_group.api.EggGroupApi
import com.kronos.pokedex.data.remote.evolution_chain.api.EvolutionChainApi
import com.kronos.pokedex.data.remote.item.api.ItemApi
import com.kronos.pokedex.data.remote.move.api.MoveApi
import com.kronos.pokedex.data.remote.nature.api.NatureApi
import com.kronos.pokedex.data.remote.pokedex.api.PokedexApi
import com.kronos.pokedex.data.remote.pokemon.api.PokemonApi
import com.kronos.pokedex.data.remote.specie.api.SpecieApi
import com.kronos.pokedex.data.remote.type.api.TypeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    fun providePokemonApi(retrofit: Retrofit): PokemonApi {
        return retrofit.create(PokemonApi::class.java)
    }

    @Provides
    fun provideAbilityApi(retrofit: Retrofit): AbilityApi {
        return retrofit.create(AbilityApi::class.java)
    }

    @Provides
    fun provideMoveApi(retrofit: Retrofit): MoveApi {
        return retrofit.create(MoveApi::class.java)
    }
    @Provides
    fun provideSpecieApi(retrofit: Retrofit): SpecieApi {
        return retrofit.create(SpecieApi::class.java)
    }

    @Provides
    fun providePokedexApi(retrofit: Retrofit): PokedexApi {
        return retrofit.create(PokedexApi::class.java)
    }

    @Provides
    fun provideEvolutionChainApi(retrofit: Retrofit): EvolutionChainApi {
        return retrofit.create(EvolutionChainApi::class.java)
    }

    @Provides
    fun provideNatureApi(retrofit: Retrofit): NatureApi {
        return retrofit.create(NatureApi::class.java)
    }

    @Provides
    fun provideItemApi(retrofit: Retrofit): ItemApi {
        return retrofit.create(ItemApi::class.java)
    }

    @Provides
    fun provideBerryApi(retrofit: Retrofit): BerryApi {
        return retrofit.create(BerryApi::class.java)
    }

    @Provides
    fun provideEggGroupApi(retrofit: Retrofit): EggGroupApi {
        return retrofit.create(EggGroupApi::class.java)
    }

    @Provides
    fun provideTypeApi(retrofit: Retrofit): TypeApi {
        return retrofit.create(TypeApi::class.java)
    }

}