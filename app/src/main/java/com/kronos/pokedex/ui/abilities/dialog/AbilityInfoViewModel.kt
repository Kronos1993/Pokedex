package com.kronos.pokedex.ui.abilities.dialog

import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kronos.core.extensions.asLiveData
import com.kronos.core.view_model.ParentViewModel
import com.kronos.logger.interfaces.ILogger
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.ability.Ability
import com.kronos.pokedex.domian.model.ability.AbilityInfo
import com.kronos.pokedex.domian.model.pokemon.PokemonDexEntry
import com.kronos.pokedex.domian.repository.AbilityRemoteRepository
import com.kronos.pokedex.ui.abilities.ShowAbilityIn
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
class AbilityInfoViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    private var abilityRemoteRepository: AbilityRemoteRepository,
    var urlProvider: UrlProvider,
    var logger: ILogger,
) : ParentViewModel() {

    private val _abilityInfo = MutableLiveData<AbilityInfo>()
    val abilityInfo = _abilityInfo.asLiveData()

    private val _origen = MutableLiveData<ShowAbilityIn?>()
    val origen = _origen.asLiveData()

    var abilityShortEffect = ObservableField<String?>()
    var abilityEffect = ObservableField<String?>()
    var abilityGameDescription = ObservableField<String?>()
    var abilityName = ObservableField<String?>()

    var pokemonListAdapter: WeakReference<PokemonListAdapter?> = WeakReference(PokemonListAdapter())
    private val _pokemonList = MutableLiveData<MutableList<PokemonDexEntry>>()
    val pokemonList = _pokemonList.asLiveData()

    fun postAbilityInfo(abilityInfo: AbilityInfo) {
        _abilityInfo.postValue(abilityInfo)
    }

    fun postOrigen(origen: ShowAbilityIn?) {
        _origen.postValue(origen)
    }

    fun getAbilityGameDescription(abilityInfo: AbilityInfo) {
        if (abilityInfo.flavorText.isNotEmpty()) {
            var find = false
            var pos = 0
            while (!find && pos < abilityInfo.flavorText.size) {
                if (abilityInfo.flavorText[pos].language == PreferencesUtil.getLanguagePreference(
                        context
                    )
                ) {
                    abilityGameDescription.set(abilityInfo.flavorText[pos].description)
                    find = true
                } else
                    pos++
            }
        }
    }

    fun getAbilityEffect(abilityInfo: AbilityInfo) {
        if (abilityInfo.effects.isNotEmpty()) {
            var find = false
            var pos = 0
            while (!find && pos < abilityInfo.effects.size) {
                if (abilityInfo.effects[pos].language == PreferencesUtil.getLanguagePreference(
                        context
                    )
                ) {
                    abilityShortEffect.set(abilityInfo.effects[pos].shortEffect)
                    abilityEffect.set(abilityInfo.effects[pos].effect)
                    find = true
                } else
                    pos++
            }
        }
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

    fun loadAbilityInfo(ability: NamedResourceApi) {
        viewModelScope.launch(Dispatchers.IO) {
            loading.postValue(true)
            var abilityInfo: AbilityInfo? = null
            abilityName.set("")

            abilityInfo = if (urlProvider.extractIdFromUrl(ability.url) != null) {
                abilityRemoteRepository.getAbility(urlProvider.extractIdFromUrl(ability.url))
            } else {
                abilityRemoteRepository.getAbility(ability.name)
            }
            postAbilityInfo(abilityInfo)
            loadPokemonList(abilityInfo)
            loading.postValue(false)
        }
    }

    fun getAbilityName(abilityInfo:AbilityInfo){
        if (abilityInfo.names.isNotEmpty()) {
            var find = false
            var pos = 0
            while (!find && pos < abilityInfo.names.size) {
                if (abilityInfo.names[pos].language.name == PreferencesUtil.getLanguagePreference(
                        context
                    )
                ) {
                    abilityName.set(abilityInfo.names[pos].name)
                    find = true
                } else
                    pos++
            }
        } else
            abilityName.set(abilityInfo.name)
    }

    fun loadPokemonList(abilityInfo: AbilityInfo) {
        var pokemonEntry = abilityInfo.pokemon.map { pokemonWithAbility ->
            PokemonDexEntry(
                urlProvider.extractIdFromUrl(pokemonWithAbility.pokemon.url),
                pokemonWithAbility.pokemon
            )
        }
        postPokemonList(pokemonEntry)
    }
}