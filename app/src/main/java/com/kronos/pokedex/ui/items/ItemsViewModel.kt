package com.kronos.pokedex.ui.items

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kronos.core.extensions.asLiveData
import com.kronos.core.view_model.ParentViewModel
import com.kronos.logger.LoggerType
import com.kronos.logger.interfaces.ILogger
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.repository.ItemRemoteRepository
import com.kronos.webclient.UrlProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import javax.inject.Inject

@HiltViewModel
class ItemsViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    private var itemRemoteRepository: ItemRemoteRepository,
    var urlProvider: UrlProvider,
    var logger: ILogger,
) : ParentViewModel() {

    private val _itemList = MutableLiveData<MutableList<NamedResourceApi>>()
    val itemList = _itemList.asLiveData()

    private val _itemOriginalList = MutableLiveData<MutableList<NamedResourceApi>>()
    val itemOriginalList = _itemOriginalList.asLiveData()

    var itemListAdapter: WeakReference<ItemListAdapter?> = WeakReference(ItemListAdapter())

    private val _limit = MutableLiveData<Int>()
    val limit = _limit.asLiveData()

    private val _offset = MutableLiveData<Int>()
    val offset = _offset.asLiveData()

    private var _total = MutableLiveData<Int>()
    val total = _total.asLiveData()

    private fun postItems(list: List<NamedResourceApi>) {
        var itemlist = mutableListOf<NamedResourceApi>()
        if (_itemList.value != null) {
            itemlist = _itemList.value!!
        }
        list.forEach {
            if (!(itemlist as ArrayList).contains(it)) {
                itemlist.add(it)
            }
        }
        _itemList.postValue(itemlist)
        loading.postValue(false)
    }

    private fun postItemsOriginalList(list: List<NamedResourceApi>) {
        var itemslist = mutableListOf<NamedResourceApi>()
        if (_itemOriginalList.value != null) {
            itemslist = _itemOriginalList.value!!
        }
        list.forEach {
            if (!(itemslist as ArrayList).contains(it)) {
                itemslist.add(it)
            }
        }
        _itemOriginalList.postValue(itemslist)
        loading.postValue(false)
    }

    fun getBerries() {
        loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            var call = async {
                val responseList = itemRemoteRepository.list(limit.value!!, offset.value!!)
                _total.postValue(responseList.count)
                postItems(responseList.results)
                postItemsOriginalList(responseList.results)
            }
            call.await()
        }
    }

    fun getMoreItems() {
        viewModelScope.launch {
            if (offset.value!! <= _total.value!!) {
                setOffset(offset.value!! + limit.value!!)
                getBerries()
            }
        }
    }

    private fun postItemListFiltered(list: List<NamedResourceApi>) {
        _itemList.postValue(list as MutableList<NamedResourceApi>?)
    }

    fun filterItems(itemName: String) {
        if (itemName.isNotEmpty()) {
            // creating a new array list to filter our data.
            val filteredList: ArrayList<NamedResourceApi> = ArrayList()
            // running a for loop to compare elements.
            for (item in _itemOriginalList.value!!) {
                // checking if the entered string matched with any item of our recycler view.
                if (item.name.lowercase().contains(itemName.lowercase())) {
                    // if the item is matched we are
                    // adding it to our filtered list.
                    filteredList.add(item)
                }
            }
            postItemListFiltered(filteredList)
        } else {
            postItemListFiltered(_itemOriginalList.value!!)
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