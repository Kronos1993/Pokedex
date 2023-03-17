package com.kronos.pokedex.ui.pokemon.list

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kronos.core.extensions.asLiveData
import com.kronos.core.view_model.ParentViewModel
import com.kronos.logger.LoggerType
import com.kronos.logger.interfaces.ILogger
import com.kronos.pokedex.domian.model.pokemon.PokemonDexEntry
import com.kronos.pokedex.domian.repository.PokedexRemoteRepository
import com.kronos.webclient.UrlProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import java.util.ArrayList
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    private var pokedexRemoteRepository: PokedexRemoteRepository,
    var urlProvider: UrlProvider,
    var logger: ILogger,
) : ParentViewModel() {

    private val _pokemonList = MutableLiveData<MutableList<PokemonDexEntry>>()
    val pokemonList = _pokemonList.asLiveData()

    private val _limit = MutableLiveData<Int>()
    val limit = _limit.asLiveData()

    private val _offset = MutableLiveData<Int>()
    val offset = _offset.asLiveData()

    private val _recyclerLastPos = MutableLiveData<Int>()
    val recyclerLastPos = _recyclerLastPos.asLiveData()

    private var _total = MutableLiveData<Int>()
    val total = _total.asLiveData()

    var pokemonListAdapter: WeakReference<PokemonListAdapter?> = WeakReference(PokemonListAdapter())

    private fun postPokemonList(list: List<PokemonDexEntry>) {
        if(_pokemonList.value!=null){
            var pokelist = _pokemonList.value!!
            list.forEach {
                if(!(pokelist as ArrayList).contains(it)){
                    pokelist.add(it)
                }
            }
            _pokemonList.postValue(pokelist as MutableList<PokemonDexEntry>?)
        }else {
            _pokemonList.postValue(list as MutableList<PokemonDexEntry>?)
        }
        loading.postValue(false)
    }

    fun getPokemons(pokedex:String) {
        loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
           var call  = async {
               val responseList = pokedexRemoteRepository.getPokedex(pokedex)
               postPokemonList(responseList.pokemons)
           }
           call.await()
        }
    }

    private fun log(item: String) {
        viewModelScope.launch {
            logger.write(this::class.java.name, LoggerType.INFO,item)
        }
    }

    fun setRecyclerLastPosition(i: Int) {
        _recyclerLastPos.value = i
    }

}