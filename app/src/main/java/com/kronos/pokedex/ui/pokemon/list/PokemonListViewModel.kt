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

    private val _pokemonOriginalList = MutableLiveData<MutableList<PokemonDexEntry>>()
    val pokemonOriginalList = _pokemonOriginalList.asLiveData()

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
        if (_pokemonList.value != null) {
            var pokelist = _pokemonList.value!!
            list.forEach {
                if (!(pokelist as ArrayList).contains(it)) {
                    pokelist.add(it)
                }
            }
            _pokemonList.postValue(pokelist as MutableList<PokemonDexEntry>?)
        } else {
            _pokemonList.postValue(list as MutableList<PokemonDexEntry>?)
        }
        loading.postValue(false)
    }

    private fun postPokemonListFiltered(list: List<PokemonDexEntry>) {
        _pokemonList.postValue(list as MutableList<PokemonDexEntry>?)
    }

    private fun postOriginalPokemonList(list: List<PokemonDexEntry>) {
        if (_pokemonOriginalList.value != null) {
            var pokelist = _pokemonOriginalList.value!!
            list.forEach {
                if (!(pokelist as ArrayList).contains(it)) {
                    pokelist.add(it)
                }
            }
            _pokemonOriginalList.postValue(pokelist as MutableList<PokemonDexEntry>?)
        } else {
            _pokemonOriginalList.postValue(list as MutableList<PokemonDexEntry>?)
        }
    }

    fun getPokemons(pokedex: String) {
        loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            var call = async {
                val responseList = pokedexRemoteRepository.getPokedex(pokedex)
                postPokemonList(responseList.pokemons)
                postOriginalPokemonList(responseList.pokemons)
            }
            call.await()
        }
    }

    fun filterPokemon(pokemonName: String) {
        if (pokemonName.isNotEmpty()) {
            // creating a new array list to filter our data.
            val filteredList: ArrayList<PokemonDexEntry> = ArrayList()
            // running a for loop to compare elements.
            for (item in _pokemonOriginalList.value!!) {
                // checking if the entered string matched with any item of our recycler view.
                if (item.pokemon.name.lowercase().contains(pokemonName.lowercase())) {
                    // if the item is matched we are
                    // adding it to our filtered list.
                    filteredList.add(item)
                }
            }
            postPokemonListFiltered(filteredList)
        } else {
            postPokemonListFiltered(_pokemonOriginalList.value!!)
        }
    }

    private fun log(item: String) {
        viewModelScope.launch {
            logger.write(this::class.java.name, LoggerType.INFO, item)
        }
    }

    fun setRecyclerLastPosition(i: Int) {
        _recyclerLastPos.value = i
    }

}