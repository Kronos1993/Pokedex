package com.kronos.pokedex.ui.items.detail

import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kronos.core.extensions.asLiveData
import com.kronos.core.view_model.ParentViewModel
import com.kronos.logger.interfaces.ILogger
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.item.ItemInfo
import com.kronos.pokedex.domian.repository.*
import com.kronos.pokedex.ui.pokemon.list.PokemonListAdapter
import com.kronos.pokedex.util.preferences.PreferencesUtil
import com.kronos.webclient.UrlProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap

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
    var itemName = ObservableField<String?>()

    var pokemonListAdapter: WeakReference<PokemonListAdapter?> = WeakReference(PokemonListAdapter())

    fun postItemInfo(itemInfo: ItemInfo) {
        _itemInfo.postValue(itemInfo)
    }

    fun loadItemInfo(item: NamedResourceApi) {
        viewModelScope.launch(Dispatchers.IO) {
            loading.postValue(true)
            var itemInfo: ItemInfo? = null
            try {
                itemInfo = if (urlProvider.extractIdFromUrl(item.url) != null) {
                    itemRemoteRepository.getItem(urlProvider.extractIdFromUrl(item.url))
                } else {
                    itemRemoteRepository.getItem(item.name)
                }
                getItemDescription(itemInfo)
                getItemEffect(itemInfo)
                postItemInfo(itemInfo)
            }catch (e:Exception){
                e.printStackTrace()
                val err: Hashtable<String, String> = Hashtable()
                err["error"] = e.toString()
                //error.postValue(err)
            }finally {
                loading.postValue(false)
            }

        }
    }

    private fun getItemDescription(itemInfo:ItemInfo){
        var find = false
        var pos = 0
        while (!find && pos < itemInfo.descriptions.size) {
            if (itemInfo.descriptions[pos].language == PreferencesUtil.getLanguagePreference(context)) {
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
            if (itemInfo.effectEntries[pos].language == PreferencesUtil.getLanguagePreference(context)) {
                itemEffect.set(itemInfo.effectEntries[pos].shortEffect)
                itemLongEffect.set(itemInfo.effectEntries[pos].effect)
                find = true
            } else
                pos++
        }
    }

    fun getItemName(item: ItemInfo){
        if (item.names.isNotEmpty()) {
            var find = false
            var pos = 0
            while (!find && pos < item.names.size) {
                if (item.names[pos].language.name == PreferencesUtil.getLanguagePreference(
                        context
                    )
                ) {
                    itemName.set(item.names[pos].name)
                    find = true
                } else
                    pos++
            }
        } else
            itemName.set(item.name)
    }
}