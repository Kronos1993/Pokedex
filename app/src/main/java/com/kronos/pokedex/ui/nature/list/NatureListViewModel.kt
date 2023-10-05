package com.kronos.pokedex.ui.nature.list

import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kronos.core.extensions.asLiveData
import com.kronos.core.view_model.ParentViewModel
import com.kronos.logger.LoggerType
import com.kronos.logger.interfaces.ILogger
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.nature.NatureDetail
import com.kronos.pokedex.domian.repository.NatureRemoteRepository
import com.kronos.webclient.UrlProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import javax.inject.Inject

@HiltViewModel
class NatureListViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    var natureRemoteRepository: NatureRemoteRepository,
    var urlProvider: UrlProvider,
    var logger: ILogger,
) : ParentViewModel()  {

    private val _natureList = MutableLiveData<MutableList<NamedResourceApi>>()
    val natureList = _natureList.asLiveData()

    private val _natureOriginalList = MutableLiveData<MutableList<NamedResourceApi>>()
    val natureOriginalList = _natureOriginalList.asLiveData()

    private val _natureInfoSelected = MutableLiveData<NatureDetail>()
    val natureInfoSelected = _natureInfoSelected.asLiveData()

    var natureListAdapter: WeakReference<NatureListAdapter?> = WeakReference(NatureListAdapter())

    private val _limit = MutableLiveData<Int>()
    val limit = _limit.asLiveData()

    private val _offset = MutableLiveData<Int>()
    val offset = _offset.asLiveData()

    private var _total = MutableLiveData<Int>()
    val total = _total.asLiveData()

    private fun postNatures(list: List<NamedResourceApi>) {
        var naturelist = mutableListOf<NamedResourceApi>()
        if(_natureList.value!=null){
            naturelist = _natureList.value!!
        }
        list.forEach {
            if(!(naturelist as ArrayList).contains(it)){
                naturelist.add(it)
            }
        }
        _natureList.postValue(naturelist)
        loading.postValue(false)
    }

    private fun postNaturesOriginalList(list: List<NamedResourceApi>) {
        var naturelist = mutableListOf<NamedResourceApi>()
        if(_natureOriginalList.value!=null){
            naturelist = _natureOriginalList.value!!
        }
        list.forEach {
            if(!(naturelist as ArrayList).contains(it)){
                naturelist.add(it)
            }
        }
        _natureOriginalList.postValue(naturelist)
        loading.postValue(false)
    }

    fun postNatureInfo(natureInfo: NatureDetail) {
        _natureInfoSelected.postValue(natureInfo)
    }

    fun getNatures() {
        loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            var call  = async {
                val responseList = natureRemoteRepository.list(limit.value!!,offset.value!!)
                _total.postValue(responseList.count)
                postNatures(responseList.results)
                postNaturesOriginalList(responseList.results)
            }
            call.await()
        }
    }

    fun getMoreNature() {
        viewModelScope.launch {
            if (offset.value!! <= _total.value!!) {
                setOffset(offset.value!! + limit.value!!)
                getNatures()
            }
        }
    }

    fun loadNatureInfo(nature: NamedResourceApi) {
        viewModelScope.launch(Dispatchers.IO) {
            loading.postValue(true)
            var natureDetail: NatureDetail? = null

            natureDetail = if (urlProvider.extractIdFromUrl(nature.url) != null) {
                natureRemoteRepository.getNature(urlProvider.extractIdFromUrl(nature.url))
            } else {
                natureRemoteRepository.getNature(nature.name)
            }
            postNatureInfo(natureDetail)
            loading.postValue(false)
        }
    }

    private fun postNatureListFiltered(list: List<NamedResourceApi>) {
        _natureList.postValue(list as MutableList<NamedResourceApi>?)
    }

    fun filterNature(natureName: String) {
        if (natureName.isNotEmpty()) {
            // creating a new array list to filter our data.
            val filteredList: ArrayList<NamedResourceApi> = ArrayList()
            // running a for loop to compare elements.
            for (item in _natureOriginalList.value!!) {
                // checking if the entered string matched with any item of our recycler view.
                if (item.name.lowercase().contains(natureName.lowercase())) {
                    // if the item is matched we are
                    // adding it to our filtered list.
                    filteredList.add(item)
                }
            }
            postNatureListFiltered(filteredList)
        } else {
            postNatureListFiltered(_natureOriginalList.value!!)
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