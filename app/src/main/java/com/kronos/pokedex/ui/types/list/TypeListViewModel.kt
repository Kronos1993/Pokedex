package com.kronos.pokedex.ui.types.list

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kronos.core.extensions.asLiveData
import com.kronos.core.view_model.ParentViewModel
import com.kronos.logger.LoggerType
import com.kronos.logger.interfaces.ILogger
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.move.MoveList
import com.kronos.pokedex.domian.model.pokemon.PokemonDexEntry
import com.kronos.pokedex.domian.model.type.TypeInfo
import com.kronos.pokedex.domian.repository.TypeRemoteRepository
import com.kronos.webclient.UrlProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import javax.inject.Inject

@HiltViewModel
class TypeListViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    private var typeRemoteRepository: TypeRemoteRepository,
    var urlProvider: UrlProvider,
    var logger: ILogger,
) : ParentViewModel() {

    private val _typeList = MutableLiveData<MutableList<NamedResourceApi>>()
    val typeList = _typeList.asLiveData()

    private val _typeOriginalList = MutableLiveData<MutableList<NamedResourceApi>>()
    val typeOriginalList = _typeOriginalList.asLiveData()

    private val _typeInfoSelected = MutableLiveData<TypeInfo>()
    val typeInfoSelected = _typeInfoSelected.asLiveData()

    private val _limit = MutableLiveData<Int>()
    val limit = _limit.asLiveData()

    private val _offset = MutableLiveData<Int>()
    val offset = _offset.asLiveData()

    private var _total = MutableLiveData<Int>()
    val total = _total.asLiveData()

    var typeListAdapter: WeakReference<TypeAdapter?> = WeakReference(TypeAdapter())

    private fun postTypes(list: List<NamedResourceApi>) {
        var typeList = mutableListOf<NamedResourceApi>()
        if (_typeList.value != null) {
            typeList = _typeList.value!!
        }
        list.forEach {
            if (!(typeList as ArrayList).contains(it)) {
                typeList.add(it)
            }
        }
        _typeList.postValue(typeList as MutableList<NamedResourceApi>?)
        loading.postValue(false)
    }

    fun postTypeInfoSelected(type: TypeInfo) {
        _typeInfoSelected.postValue(type)
    }

    private fun postTypeListFiltered(list: List<NamedResourceApi>) {
        _typeList.postValue(list as MutableList<NamedResourceApi>?)
    }

    private fun postOriginalTypeList(list: List<NamedResourceApi>) {
        var typeList = mutableListOf<NamedResourceApi>()
        if (_typeOriginalList.value != null) {
            typeList = _typeOriginalList.value!!
        }
        list.forEach {
            if (!(typeList as ArrayList).contains(it)) {
                typeList.add(it)
            }
        }
        _typeOriginalList.postValue(typeList as MutableList<NamedResourceApi>?)
    }

    fun getTypes() {
        loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            var call = async {
                val responseList = typeRemoteRepository.list(limit.value!!,offset.value!!)
                _total.postValue(responseList.count)
                postTypes(responseList.results)
                postOriginalTypeList(responseList.results)
            }
            call.await()
        }
    }

    fun getMoreTypes() {
        viewModelScope.launch {
            if (offset.value!! <= _total.value!!) {
                setOffset(offset.value!! + limit.value!!)
                getTypes()
            }
        }
    }

    fun loadTypeInfo(type: NamedResourceApi) {
        viewModelScope.launch(Dispatchers.IO) {
            loading.postValue(true)
            var typeInfo: TypeInfo = if(!type.url.isNullOrEmpty()){
                if (urlProvider.extractIdFromUrl(type.url) != null) {
                    typeRemoteRepository.getTypeInfo(urlProvider.extractIdFromUrl(type.url))
                } else {
                    typeRemoteRepository.getTypeInfo(type.name)
                }
            }else{
                typeRemoteRepository.getTypeInfo(type.name)
            }
            postTypeInfoSelected(typeInfo)
            loading.postValue(false)
        }
    }

    fun setLimit(i: Int) {
        _limit.value = i
    }

    fun setOffset(i: Int) {
        _offset.value = i
    }

    fun filterTypes(typeName: String) {
        if (typeName.isNotEmpty()) {
            // creating a new array list to filter our data.
            val filteredList: ArrayList<NamedResourceApi> = ArrayList()
            // running a for loop to compare elements.
            for (item in _typeOriginalList.value!!) {
                // checking if the entered string matched with any item of our recycler view.
                if (item.name.lowercase().contains(typeName.lowercase())) {
                    // if the item is matched we are
                    // adding it to our filtered list.
                    filteredList.add(item)
                }
            }
            postTypeListFiltered(filteredList)
        } else {
            postTypeListFiltered(_typeOriginalList.value!!)
        }
    }

    private fun log(item: String) {
        viewModelScope.launch {
            logger.write(this::class.java.name, LoggerType.INFO, item)
        }
    }

}