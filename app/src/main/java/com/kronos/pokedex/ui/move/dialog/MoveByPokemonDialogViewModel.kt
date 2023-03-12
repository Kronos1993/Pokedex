package com.kronos.pokedex.ui.move.dialog

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kronos.core.extensions.asLiveData
import com.kronos.core.view_model.ParentViewModel
import com.kronos.logger.LoggerType
import com.kronos.logger.interfaces.ILogger
import com.kronos.pokedex.domian.model.move.MoveList
import com.kronos.pokedex.domian.model.pokemon.PokemonList
import com.kronos.pokedex.domian.repository.PokemonRemoteRepository
import com.kronos.pokedex.ui.move.list.PokemonMoveListAdapter
import com.kronos.pokedex.ui.pokemon.list.PokemonListAdapter
import com.kronos.webclient.UrlProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import javax.inject.Inject

@HiltViewModel
class MoveByPokemonDialogViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    var logger: ILogger
) : ParentViewModel() {

    private val _pokemonMoveList = MutableLiveData<MutableList<MoveList>>()
    val pokemonMoveList = _pokemonMoveList.asLiveData()

    var moveByPokemonAdapter: WeakReference<PokemonMoveListAdapter?> = WeakReference(PokemonMoveListAdapter())

    fun postPokemonMove(list: List<MoveList>) {
        _pokemonMoveList.postValue(list as MutableList<MoveList>?)
        loading.postValue(false)
    }


    private fun log(item: String) {
        viewModelScope.launch {
            logger.write(this::class.java.name, LoggerType.INFO, item)
        }
    }
}