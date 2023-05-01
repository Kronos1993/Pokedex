package com.kronos.pokedex.ui.egg_group.list

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kronos.core.extensions.asLiveData
import com.kronos.core.view_model.ParentViewModel
import com.kronos.logger.LoggerType
import com.kronos.logger.interfaces.ILogger
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.repository.EggGroupRemoteRepository
import com.kronos.pokedex.ui.pokemon.detail.adapter.PokemonEggGroupAdapter
import com.kronos.webclient.UrlProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import javax.inject.Inject

@HiltViewModel
class EggGroupsViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    private var eggGroupRemoteRepository: EggGroupRemoteRepository,
    var urlProvider: UrlProvider,
    var logger: ILogger,
) : ParentViewModel() {

    private val _eggGroupList = MutableLiveData<MutableList<NamedResourceApi>>()
    val eggGroupList = _eggGroupList.asLiveData()

    private val _eggGroupOriginalList = MutableLiveData<MutableList<NamedResourceApi>>()
    val eggGroupOriginalList = _eggGroupOriginalList.asLiveData()

    var eggGroupAdapter: WeakReference<PokemonEggGroupAdapter?> = WeakReference(PokemonEggGroupAdapter())

    private val _limit = MutableLiveData<Int>()
    val limit = _limit.asLiveData()

    private val _offset = MutableLiveData<Int>()
    val offset = _offset.asLiveData()

    private var _total = MutableLiveData<Int>()
    val total = _total.asLiveData()

    private fun postEggGroups(list: List<NamedResourceApi>) {
        var itemlist = mutableListOf<NamedResourceApi>()
        if (_eggGroupList.value != null) {
            itemlist = _eggGroupList.value!!
        }
        list.forEach {
            if (!(itemlist as ArrayList).contains(it)) {
                itemlist.add(it)
            }
        }
        _eggGroupList.postValue(itemlist)
        loading.postValue(false)
    }

    private fun postEggGroupOriginalList(list: List<NamedResourceApi>) {
        var itemslist = mutableListOf<NamedResourceApi>()
        if (_eggGroupOriginalList.value != null) {
            itemslist = _eggGroupOriginalList.value!!
        }
        list.forEach {
            if (!(itemslist as ArrayList).contains(it)) {
                itemslist.add(it)
            }
        }
        _eggGroupOriginalList.postValue(itemslist)
        loading.postValue(false)
    }

    fun getEggGroups() {
        loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            var call = async {
                val responseList = eggGroupRemoteRepository.list(limit.value!!, offset.value!!)
                _total.postValue(responseList.count)
                postEggGroups(responseList.results)
                postEggGroupOriginalList(responseList.results)
            }
            call.await()
        }
    }

    fun getMoreEggGroups() {
        viewModelScope.launch {
            if (offset.value!! <= _total.value!!) {
                setOffset(offset.value!! + limit.value!!)
                getEggGroups()
            }
        }
    }

    private fun postEggGroupsFiltered(list: List<NamedResourceApi>) {
        _eggGroupList.postValue(list as MutableList<NamedResourceApi>?)
    }

    fun filterEggGroups(eggGroupName: String) {
        if (eggGroupName.isNotEmpty()) {
            // creating a new array list to filter our data.
            val filteredList: ArrayList<NamedResourceApi> = ArrayList()
            // running a for loop to compare elements.
            for (item in _eggGroupOriginalList.value!!) {
                // checking if the entered string matched with any item of our recycler view.
                if (item.name.lowercase().contains(eggGroupName.lowercase())) {
                    // if the item is matched we are
                    // adding it to our filtered list.
                    filteredList.add(item)
                }
            }
            postEggGroupsFiltered(filteredList)
        } else {
            postEggGroupsFiltered(_eggGroupOriginalList.value!!)
        }
    }

    private fun log(item: String) {
        viewModelScope.launch {
            logger.write(this::class.java.name, LoggerType.INFO, item)
        }
    }

    fun setLimit(i: Int) {
        _limit.value = i
    }

    fun setOffset(i: Int) {
        _offset.value = i
    }
}