package com.kronos.pokedex.ui.move.dialog

import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kronos.core.extensions.asLiveData
import com.kronos.core.view_model.ParentViewModel
import com.kronos.logger.interfaces.ILogger
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.move.MoveInfo
import com.kronos.pokedex.domian.model.pokemon.PokemonDexEntry
import com.kronos.pokedex.domian.model.type.Type
import com.kronos.pokedex.domian.repository.MoveRemoteRepository
import com.kronos.pokedex.ui.move.ShowMoveIn
import com.kronos.pokedex.ui.pokemon.list.PokemonListAdapter
import com.kronos.pokedex.util.preferences.PreferencesUtil
import com.kronos.webclient.UrlProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import javax.inject.Inject

@HiltViewModel
class MoveInfoViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    private var moveRemoteRepository: MoveRemoteRepository,
    var urlProvider: UrlProvider,
    var logger: ILogger,
) : ParentViewModel() {

    private val _moveInfo = MutableLiveData<MoveInfo>()
    val moveInfo = _moveInfo.asLiveData()

    private val _origen = MutableLiveData<ShowMoveIn?>()
    val origen = _origen.asLiveData()

    var moveShortEffect = ObservableField<String?>()
    var moveEffect = ObservableField<String?>()
    var moveDescription = ObservableField<String?>()
    var type = ObservableField<Type?>()
    var moveName = ObservableField<String?>()

    var pokemonListAdapter: WeakReference<PokemonListAdapter?> = WeakReference(PokemonListAdapter())
    private val _pokemonList = MutableLiveData<MutableList<PokemonDexEntry>>()
    val pokemonList = _pokemonList.asLiveData()

    fun postMoveInfo(moveInfo: MoveInfo) {
        _moveInfo.postValue(moveInfo)
    }

    fun postOrigen(origen: ShowMoveIn?) {
        _origen.postValue(origen)
    }

    fun getMoveGameDescription(moveInfo: MoveInfo) {
        if (moveInfo.moveFlavorText.isNotEmpty()) {
            var find = false
            var pos = 0
            while (!find && pos < moveInfo.moveFlavorText.size) {
                if (moveInfo.moveFlavorText[pos].language == PreferencesUtil.getLanguagePreference(context)) {
                    moveDescription.set(moveInfo.moveFlavorText[pos].description)
                    find = true
                } else
                    pos++
            }
        }
    }

    fun getMoveEffect(moveInfo: MoveInfo) {
        if (moveInfo.effects.isNotEmpty()) {
            var find = false
            var pos = 0
            while (!find && pos < moveInfo.effects.size) {
                if (moveInfo.effects[pos].language == PreferencesUtil.getLanguagePreference(context)) {
                    moveShortEffect.set(moveInfo.effects[pos].shortEffect)
                    moveEffect.set(moveInfo.effects[pos].effect)
                    find = true
                } else
                    pos++
            }
        }
    }

    fun getMoveName(moveInfo:MoveInfo){
        if (moveInfo.names.isNotEmpty()) {
            var find = false
            var pos = 0
            while (!find && pos < moveInfo.names.size) {
                if (moveInfo.names[pos].language.name == PreferencesUtil.getLanguagePreference(
                        context
                    )
                ) {
                    moveName.set(moveInfo.names[pos].name)
                    find = true
                } else
                    pos++
            }
        } else
            moveName.set(moveInfo.moveName)
    }

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
    }

    fun loadMoveInfo(move: NamedResourceApi) {
        viewModelScope.launch(Dispatchers.IO) {
            loading.postValue(true)
            var moveInfo: MoveInfo? = null

            moveInfo = if (urlProvider.extractIdFromUrl(move.url) != null) {
                moveRemoteRepository.getMove(urlProvider.extractIdFromUrl(move.url))
            } else {
                moveRemoteRepository.getMove(move.name)
            }
            postMoveInfo(moveInfo)
            loadPokemonList(moveInfo)
            loading.postValue(false)
        }
    }

    fun loadPokemonList(moveInfo: MoveInfo){
        var pokemonEntry = moveInfo.learnedBy.map {
            PokemonDexEntry(urlProvider.extractIdFromUrl(it.url), it)
        }
        postPokemonList(pokemonEntry)
    }
}