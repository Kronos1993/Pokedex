package com.kronos.pokedex.ui.pokemon.detail

import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kronos.core.extensions.asLiveData
import com.kronos.core.view_model.ParentViewModel
import com.kronos.logger.interfaces.ILogger
import com.kronos.pokedex.domian.model.pokemon.PokemonInfo
import com.kronos.pokedex.domian.model.pokemon.PokemonList
import com.kronos.pokedex.domian.model.specie.SpecieInfo
import com.kronos.pokedex.domian.repository.PokemonRemoteRepository
import com.kronos.pokedex.domian.repository.SpecieRemoteRepository
import com.kronos.pokedex.ui.abilities.PokemonAbilityAdapter
import com.kronos.pokedex.ui.pokemon.list.PokemonListAdapter
import com.kronos.pokedex.ui.tms.PokemonStatsAdapter
import com.kronos.pokedex.ui.types.PokemonTypeAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel  @Inject constructor(
    @ApplicationContext val context: Context,
    private var pokemonRemoteRepository: PokemonRemoteRepository,
    private var pokemonSpecieRemoteRepository: SpecieRemoteRepository,
    var logger: ILogger,
) : ParentViewModel() {

    private val _pokemonInfo = MutableLiveData<PokemonInfo>()
    val pokemonInfo = _pokemonInfo.asLiveData()

    private val _pokemonSpritesUrl = MutableLiveData<List<String>>()
    val pokemonSpritesUrl = _pokemonSpritesUrl.asLiveData()

    var pokemonTypeAdapter: WeakReference<PokemonTypeAdapter?> = WeakReference(PokemonTypeAdapter())

    var pokemonAbilityAdapter: WeakReference<PokemonAbilityAdapter?> = WeakReference(PokemonAbilityAdapter())

    var pokemonStatAdapter: WeakReference<PokemonStatsAdapter?> = WeakReference(PokemonStatsAdapter())

    var pokemonSpriteAdapter: WeakReference<PokemonSpriteAdapter?> = WeakReference(PokemonSpriteAdapter())

    var statsTotal = ObservableField<Int?>()
    var pokemonDescription = ObservableField<String?>()

    private fun postPokemonInfo(pokemonInfo: PokemonInfo) {
        _pokemonInfo.postValue(pokemonInfo)
    }

    private fun postPokemonSprites(pokemonSprites: List<String>) {
        _pokemonSpritesUrl.postValue(pokemonSprites)
    }

    fun loadPokemonInfo(pokemonList: PokemonList) {
        viewModelScope.launch(Dispatchers.IO) {
            loading.postValue(true)
            var pokemonInfo = pokemonRemoteRepository.getPokemonInfo(pokemonList.name)

            var specie = pokemonSpecieRemoteRepository.getSpecieInfo(pokemonInfo.id)

            if(specie!=null){
                pokemonInfo.specie = specie
                if(specie.description.isNotEmpty()){
                    var find = false
                    var pos = 0
                    while(!find && pos<specie.description.size){
                        if(pokemonInfo.specie.description[pos].language == "en"){
                            pokemonDescription.set(pokemonInfo.specie.description[pos].description)
                            find = true
                        }else
                            pos++
                    }

                }
            }
            postPokemonInfo(pokemonInfo)

            var pokemonSprite = mutableListOf<String>()
            pokemonInfo.sprites.frontHome.let {
             if (!it.isNullOrEmpty()){
                 pokemonSprite.add(it)
             }
            }
            pokemonInfo.sprites.frontHomeShiny.let {
                if (!it.isNullOrEmpty()){
                    pokemonSprite.add(it)
                }
            }
            pokemonInfo.sprites.frontDefault.let {
                if (!it.isNullOrEmpty()){
                    pokemonSprite.add(it)
                }
            }
            pokemonInfo.sprites.frontFemale.let {
                if (!it.isNullOrEmpty()){
                    pokemonSprite.add(it)
                }
            }
            pokemonInfo.sprites.frontShiny.let {
                if (!it.isNullOrEmpty()){
                    pokemonSprite.add(it)
                }
            }
            pokemonInfo.sprites.frontShinyFemale.let {
                if (!it.isNullOrEmpty()){
                    pokemonSprite.add(it)
                }
            }
            postPokemonSprites(pokemonSprite)

            statsTotal.set(0)
            pokemonInfo.stats.forEach { statsTotal.set(statsTotal.get()?.plus(it.baseStat)) }
            loading.postValue(false)
        }
    }
}