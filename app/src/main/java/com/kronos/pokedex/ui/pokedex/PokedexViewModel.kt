package com.kronos.pokedex.ui.pokedex

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kronos.core.extensions.asLiveData
import com.kronos.core.view_model.ParentViewModel
import com.kronos.logger.LoggerType
import com.kronos.logger.interfaces.ILogger
import com.kronos.pokedex.domian.model.ResponseListItem
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

    private val _pokedexList = MutableLiveData<MutableList<ResponseListItem>>()
    val pokedexList = _pokedexList.asLiveData()

    var pokedexListAdapter: WeakReference<ItemListAdapter?> = WeakReference(ItemListAdapter())

    private val _limit = MutableLiveData<Int>()
    val limit = _limit.asLiveData()

    private val _offset = MutableLiveData<Int>()
    val offset = _offset.asLiveData()

    private var _total = MutableLiveData<Int>()
    val total = _total.asLiveData()

    private fun postPokedex(list: List<ResponseListItem>) {
        var pokelist = mutableListOf<ResponseListItem>()
        if(_pokedexList.value!=null){
            pokelist = _pokedexList.value!!
        }
        list.forEach {
            if(!(pokelist as ArrayList).contains(it)){
                if(!it.name.equals("hoenn",true) && !it.name.equals("johto",false) && !it.name.contains("original-") && !it.name.contains("conquest-gallery") && !it.name.contains("letsgo-kanto"))
                    pokelist.add(it)
            }
        }
        _pokedexList.postValue(pokelist as MutableList<ResponseListItem>?)
        loading.postValue(false)
    }

    fun getPokedex() {
        loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            var call  = async {
                val responseList = pokedexRemoteRepository.list(limit.value!!,offset.value!!)
                _total.postValue(responseList.count)
                postPokedex(responseList.results)
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