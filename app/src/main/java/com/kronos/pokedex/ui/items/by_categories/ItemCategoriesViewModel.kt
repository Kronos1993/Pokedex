package com.kronos.pokedex.ui.items.by_categories

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
class ItemCategoriesViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    private var itemRemoteRepository: ItemRemoteRepository,
    var urlProvider: UrlProvider,
    var logger: ILogger,
) : ParentViewModel() {

    private val _itemCategoryList = MutableLiveData<MutableList<NamedResourceApi>>()
    val itemCategory = _itemCategoryList.asLiveData()

    private val _itemCategoryOriginalList = MutableLiveData<MutableList<NamedResourceApi>>()
    val itemCategoryOriginalList = _itemCategoryOriginalList.asLiveData()

    var itemCategoryListAdapter: WeakReference<ItemByCategoriesListAdapter?> = WeakReference(ItemByCategoriesListAdapter())

    private val _limit = MutableLiveData<Int>()
    val limit = _limit.asLiveData()

    private val _offset = MutableLiveData<Int>()
    val offset = _offset.asLiveData()

    private var _total = MutableLiveData<Int>()
    val total = _total.asLiveData()

    private fun postItemByCategories(list: List<NamedResourceApi>) {
        var itemlist = mutableListOf<NamedResourceApi>()
        if (_itemCategoryList.value != null) {
            itemlist = _itemCategoryList.value!!
        }
        list.forEach {
            if (!(itemlist as ArrayList).contains(it)) {
                itemlist.add(it)
            }
        }
        _itemCategoryList.postValue(itemlist)
        loading.postValue(false)
    }

    private fun postItemByCategoriesOriginalList(list: List<NamedResourceApi>) {
        var itemslist = mutableListOf<NamedResourceApi>()
        if (_itemCategoryOriginalList.value != null) {
            itemslist = _itemCategoryOriginalList.value!!
        }
        list.forEach {
            if (!(itemslist as ArrayList).contains(it)) {
                itemslist.add(it)
            }
        }
        _itemCategoryOriginalList.postValue(itemslist)
        loading.postValue(false)
    }

    fun getItemCategories() {
        loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            var call = async {
                val responseList = itemRemoteRepository.listItemCategories(limit.value!!, offset.value!!)
                _total.postValue(responseList.count)
                postItemByCategories(responseList.results)
                postItemByCategoriesOriginalList(responseList.results)
            }
            call.await()
        }
    }

    fun getMoreItemCategories() {
        viewModelScope.launch {
            if (offset.value!! <= _total.value!!) {
                setOffset(offset.value!! + limit.value!!)
                getItemCategories()
            }
        }
    }

    private fun postItemCategoriesFiltered(list: List<NamedResourceApi>) {
        _itemCategoryList.postValue(list as MutableList<NamedResourceApi>?)
    }

    fun filterItemCategories(itemName: String) {
        if (itemName.isNotEmpty()) {
            // creating a new array list to filter our data.
            val filteredList: ArrayList<NamedResourceApi> = ArrayList()
            // running a for loop to compare elements.
            for (item in _itemCategoryOriginalList.value!!) {
                // checking if the entered string matched with any item of our recycler view.
                if (item.name.lowercase().contains(itemName.lowercase())) {
                    // if the item is matched we are
                    // adding it to our filtered list.
                    filteredList.add(item)
                }
            }
            postItemCategoriesFiltered(filteredList)
        } else {
            postItemCategoriesFiltered(_itemCategoryOriginalList.value!!)
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