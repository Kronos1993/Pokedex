package com.kronos.pokedex.ui.berries

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kronos.core.extensions.asLiveData
import com.kronos.core.view_model.ParentViewModel
import com.kronos.logger.LoggerType
import com.kronos.logger.interfaces.ILogger
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.repository.BerryRemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import javax.inject.Inject

@HiltViewModel
class BerriesViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    private var berryRemoteRepository: BerryRemoteRepository,
    var logger: ILogger,
) : ParentViewModel() {

    private val _berryList = MutableLiveData<MutableList<NamedResourceApi>>()
    val berryList = _berryList.asLiveData()

    private val _berryOriginalList = MutableLiveData<MutableList<NamedResourceApi>>()
    val berryOriginalList = _berryOriginalList.asLiveData()

    var berryListAdapter: WeakReference<BerryListAdapter?> = WeakReference(BerryListAdapter())

    private val _limit = MutableLiveData<Int>()
    val limit = _limit.asLiveData()

    private val _offset = MutableLiveData<Int>()
    val offset = _offset.asLiveData()

    private var _total = MutableLiveData<Int>()
    val total = _total.asLiveData()

    private fun postBerries(list: List<NamedResourceApi>) {
        var berrylist = mutableListOf<NamedResourceApi>()
        if (_berryList.value != null) {
            berrylist = _berryList.value!!
        }
        list.forEach {
            if (!(berrylist as ArrayList).contains(it)) {
                berrylist.add(it)
            }
        }
        _berryList.postValue(berrylist)
        loading.postValue(false)
    }

    private fun postBerryOriginalList(list: List<NamedResourceApi>) {
        var berrylist = mutableListOf<NamedResourceApi>()
        if (_berryOriginalList.value != null) {
            berrylist = _berryOriginalList.value!!
        }
        list.forEach {
            if (!(berrylist as ArrayList).contains(it)) {
                berrylist.add(it)
            }
        }
        _berryOriginalList.postValue(berrylist)
        loading.postValue(false)
    }

    fun getBerries() {
        loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            var call = async {
                val responseList = berryRemoteRepository.list(limit.value!!, offset.value!!)
                _total.postValue(responseList.count)
                postBerries(responseList.results)
                postBerryOriginalList(responseList.results)
            }
            call.await()
        }
    }

    fun getMoreBerries() {
        viewModelScope.launch {
            if (offset.value!! <= _total.value!!) {
                setOffset(offset.value!! + limit.value!!)
                getBerries()
            }
        }
    }

    private fun postBerriesListFiltered(list: List<NamedResourceApi>) {
        _berryList.postValue(list as MutableList<NamedResourceApi>?)
    }

    fun filterBerries(berryName: String) {
        if (berryName.isNotEmpty()) {
            // creating a new array list to filter our data.
            val filteredList: ArrayList<NamedResourceApi> = ArrayList()
            // running a for loop to compare elements.
            for (item in _berryOriginalList.value!!) {
                // checking if the entered string matched with any item of our recycler view.
                if (item.name.lowercase().contains(berryName.lowercase())) {
                    // if the item is matched we are
                    // adding it to our filtered list.
                    filteredList.add(item)
                }
            }
            postBerriesListFiltered(filteredList)
        } else {
            postBerriesListFiltered(_berryOriginalList.value!!)
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