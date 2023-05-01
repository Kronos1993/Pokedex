package com.kronos.pokedex.ui.egg_group.detail

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kronos.core.extensions.asLiveData
import com.kronos.core.view_model.ParentViewModel
import com.kronos.logger.interfaces.ILogger
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.egg_group.EggGroupInfo
import com.kronos.pokedex.domian.model.item.ItemInfo
import com.kronos.pokedex.domian.repository.*
import com.kronos.pokedex.ui.pokemon.list.PokemonListAdapter
import com.kronos.webclient.UrlProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import javax.inject.Inject

@HiltViewModel
class EggGroupDetailViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private var eggGroupRemoteRepository: EggGroupRemoteRepository,
    var urlProvider: UrlProvider,
    var logger: ILogger,
) : ParentViewModel() {

    private val _eggGroupInfo = MutableLiveData<EggGroupInfo>()
    val eggGroupInfo = _eggGroupInfo.asLiveData()

    var pokemonListAdapter: WeakReference<PokemonListAdapter?> = WeakReference(PokemonListAdapter())

    fun postEggGroupInfo(eggGroup: EggGroupInfo) {
        _eggGroupInfo.postValue(eggGroup)
    }

    fun loadEggGroupInfo(eggGroup: NamedResourceApi) {
        viewModelScope.launch(Dispatchers.IO) {
            loading.postValue(true)
            var eggGroupInfo: EggGroupInfo? = null

            eggGroupInfo = if (urlProvider.extractIdFromUrl(eggGroup.url) != null) {
                eggGroupRemoteRepository.getEggGroup(urlProvider.extractIdFromUrl(eggGroup.url))
            } else {
                eggGroupRemoteRepository.getEggGroup(eggGroup.name)
            }
            postEggGroupInfo(eggGroupInfo)
            loading.postValue(false)
        }
    }

}