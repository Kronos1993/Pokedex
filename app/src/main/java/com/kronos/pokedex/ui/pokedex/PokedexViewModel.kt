package com.kronos.pokedex.ui.pokedex

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kronos.core.extensions.asLiveData
import com.kronos.core.view_model.ParentViewModel
import com.kronos.logger.LoggerType
import com.kronos.logger.interfaces.ILogger
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.repository.PokedexRemoteRepository
import com.kronos.pokedex.ui.ItemListAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import javax.inject.Inject

@HiltViewModel
class PokedexViewModel  @Inject constructor(
    @ApplicationContext val context: Context,
    private var pokedexRemoteRepository: PokedexRemoteRepository,
    var logger: ILogger,
) : ParentViewModel() {

    private val _pokedexList = MutableLiveData<MutableList<NamedResourceApi>>()
    val pokedexList = _pokedexList.asLiveData()

    private val _pokedexOriginalList = MutableLiveData<MutableList<NamedResourceApi>>()
    val pokedexOriginalList = _pokedexOriginalList.asLiveData()

    var pokedexListAdapter: WeakReference<ItemListAdapter?> = WeakReference(ItemListAdapter())

    private val _limit = MutableLiveData<Int>()
    val limit = _limit.asLiveData()

    private val _offset = MutableLiveData<Int>()
    val offset = _offset.asLiveData()

    private var _total = MutableLiveData<Int>()
    val total = _total.asLiveData()

    private fun postPokedex(list: List<NamedResourceApi>) {
        var pokelist = mutableListOf<NamedResourceApi>()
        if(_pokedexList.value!=null){
            pokelist = _pokedexList.value!!
        }
        list.forEach {
            if(!(pokelist as ArrayList).contains(it)){
                if(!it.name.equals("hoenn",true) && !it.name.equals("johto",false) && !it.name.contains("original-") && !it.name.contains("conquest-gallery") && !it.name.contains("letsgo-kanto"))
                    pokelist.add(it)
            }
        }
        _pokedexList.postValue(pokelist)
        loading.postValue(false)
    }

    private fun postPokedexOriginalList(list: List<NamedResourceApi>) {
        var pokelist = mutableListOf<NamedResourceApi>()
        if(_pokedexOriginalList.value!=null){
            pokelist = _pokedexOriginalList.value!!
        }
        list.forEach {
            if(!(pokelist as ArrayList).contains(it)){
                if(!it.name.equals("hoenn",true) && !it.name.equals("johto",false) && !it.name.contains("original-") && !it.name.contains("conquest-gallery") && !it.name.contains("letsgo-kanto"))
                    pokelist.add(it)
            }
        }
        _pokedexOriginalList.postValue(pokelist)
        loading.postValue(false)
    }

    fun getPokedex() {
        loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            var call  = async {
                val responseList = pokedexRemoteRepository.list(limit.value!!,offset.value!!)
                _total.postValue(responseList.count)
                postPokedex(responseList.results)
                postPokedexOriginalList(responseList.results)
            }
            call.await()
        }
    }

    fun getMorePokedex() {
        viewModelScope.launch {
            if (offset.value!! <= _total.value!!) {
                setOffset(offset.value!! + limit.value!!)
                getPokedex()
            }
        }
    }

    private fun postPokedexListFiltered(list: List<NamedResourceApi>) {
        _pokedexList.postValue(list as MutableList<NamedResourceApi>?)
    }

    fun filterPokedex(pokedexName: String) {
        if (pokedexName.isNotEmpty()) {
            // creating a new array list to filter our data.
            val filteredList: ArrayList<NamedResourceApi> = ArrayList()
            // running a for loop to compare elements.
            for (item in _pokedexOriginalList.value!!) {
                // checking if the entered string matched with any item of our recycler view.
                if (item.name.lowercase().contains(pokedexName.lowercase())) {
                    // if the item is matched we are
                    // adding it to our filtered list.
                    filteredList.add(item)
                }
            }
            postPokedexListFiltered(filteredList)
        } else {
            postPokedexListFiltered(_pokedexOriginalList.value!!)
        }
    }

    private fun log(item: String) {
        viewModelScope.launch {
            logger.write(this::class.java.name, LoggerType.INFO,item)
        }
    }
    fun setLimit(i: Int) {
        _limit.value = i
    }

    fun setOffset(i: Int) {
        _offset.value = i
    }

}