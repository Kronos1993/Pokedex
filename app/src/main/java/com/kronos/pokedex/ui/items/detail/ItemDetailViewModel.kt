package com.kronos.pokedex.ui.items.detail

import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kronos.core.extensions.asLiveData
import com.kronos.core.view_model.ParentViewModel
import com.kronos.logger.interfaces.ILogger
import com.kronos.pokedex.R
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.ability.AbilityInfo
import com.kronos.pokedex.domian.model.evolution_chain.ChainLink
import com.kronos.pokedex.domian.model.evolution_chain.EvolutionChain
import com.kronos.pokedex.domian.model.item.ItemInfo
import com.kronos.pokedex.domian.model.move.MoveInfo
import com.kronos.pokedex.domian.model.pokemon.PokemonInfo
import com.kronos.pokedex.domian.model.pokemon.extension.totalStat
import com.kronos.pokedex.domian.model.specie.SpecieInfo
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
class ItemDetailViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private var itemRemoteRepository: ItemRemoteRepository,
    var urlProvider: UrlProvider,
    var logger: ILogger,
) : ParentViewModel() {

    private val _itemInfo = MutableLiveData<ItemInfo>()
    val itemInfo = _itemInfo.asLiveData()

    val itemDescription = ObservableField<String?>()
    val itemEffect = ObservableField<String?>()
    val itemLongEffect = ObservableField<String?>()

    var pokemonListAdapter: WeakReference<PokemonListAdapter?> = WeakReference(PokemonListAdapter())

    fun postItemInfo(itemInfo: ItemInfo) {
        _itemInfo.postValue(itemInfo)
    }

    fun loadItemInfo(item: NamedResourceApi) {
        viewModelScope.launch(Dispatchers.IO) {
            loading.postValue(true)
            var itemInfo: ItemInfo? = null

            itemInfo = if (urlProvider.extractIdFromUrl(item.url) != null) {
                itemRemoteRepository.getItem(urlProvider.extractIdFromUrl(item.url))
            } else {
                itemRemoteRepository.getItem(item.name)
            }
            getItemDescription(itemInfo)
            getItemEffect(itemInfo)
            postItemInfo(itemInfo)
            loading.postValue(false)
        }
    }

    private fun getItemDescription(itemInfo:ItemInfo){
        var find = false
        var pos = 0
        while (!find && pos < itemInfo.descriptions.size) {
            if (itemInfo.descriptions[pos].language == "en") {
                itemDescription.set(itemInfo.descriptions[pos].description)
                find = true
            } else
                pos++
        }
    }

    private fun getItemEffect(itemInfo:ItemInfo){
        var find = false
        var pos = 0
        while (!find && pos < itemInfo.effectEntries.size) {
            if (itemInfo.effectEntries[pos].language == "en") {
                itemEffect.set(itemInfo.effectEntries[pos].shortEffect)
                itemLongEffect.set(itemInfo.effectEntries[pos].effect)
                find = true
            } else
                pos++
        }
    }
}