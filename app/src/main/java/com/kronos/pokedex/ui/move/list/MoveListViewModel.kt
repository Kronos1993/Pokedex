package com.kronos.pokedex.ui.move.list

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kronos.core.extensions.asLiveData
import com.kronos.core.view_model.ParentViewModel
import com.kronos.logger.LoggerType
import com.kronos.logger.interfaces.ILogger
import com.kronos.pokedex.domian.model.move.MoveList
import com.kronos.pokedex.domian.repository.MoveRemoteRepository
import com.kronos.pokedex.ui.move.PokemonMoveListAdapter
import com.kronos.webclient.UrlProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import javax.inject.Inject

@HiltViewModel
class MoveListViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    private var moveRemoteRepository: MoveRemoteRepository,
    var urlProvider: UrlProvider,
    var logger: ILogger,
) : ParentViewModel()  {


    private val _moveList = MutableLiveData<MutableList<MoveList>>()
    val moveList = _moveList.asLiveData()

    private val _moveOriginalList = MutableLiveData<MutableList<MoveList>>()
    val moveOriginalList = _moveOriginalList.asLiveData()

    private val _limit = MutableLiveData<Int>()
    val limit = _limit.asLiveData()

    private val _offset = MutableLiveData<Int>()
    val offset = _offset.asLiveData()

    private var _total = MutableLiveData<Int>()
    val total = _total.asLiveData()

    var moveAdapter: WeakReference<PokemonMoveListAdapter?> =
        WeakReference(PokemonMoveListAdapter())

    private fun postMoveList(list: List<MoveList>) {
        if (_moveList.value != null) {
            var moveList = _moveList.value!!
            list.forEach {
                if (!(moveList as ArrayList).contains(it)) {
                    moveList.add(it)
                }
            }
            _moveList.postValue(moveList as MutableList<MoveList>?)
        } else {
            _moveList.postValue(list as MutableList<MoveList>?)
        }
        loading.postValue(false)
    }

    private fun postMoveListFiltered(list: List<MoveList>) {
        _moveList.postValue(list as MutableList<MoveList>?)
    }

    private fun postOriginalMoveList(list: List<MoveList>) {
        if (_moveOriginalList.value != null) {
            var moveList = _moveOriginalList.value!!
            list.forEach {
                if (!(moveList as ArrayList).contains(it)) {
                    moveList.add(it)
                }
            }
            _moveOriginalList.postValue(moveList as MutableList<MoveList>?)
        } else {
            _moveOriginalList.postValue(list as MutableList<MoveList>?)
        }
    }

    fun getMoves() {
        loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            var call = async {
                val responseList = moveRemoteRepository.list(limit.value!!,offset.value!!)
                _total.postValue(responseList.count)
                postOriginalMoveList(responseList.results.map { MoveList(it) })
                postMoveList(responseList.results.map { MoveList(it) })
            }
            call.await()
        }
    }

    fun filterMove(moveName: String) {
        if (moveName.isNotEmpty()) {
            // creating a new array list to filter our data.
            val filteredList: ArrayList<MoveList> = ArrayList()
            // running a for loop to compare elements.
            for (item in _moveOriginalList.value!!) {
                // checking if the entered string matched with any item of our recycler view.
                if (item.move.name.lowercase().contains(moveName.lowercase())) {
                    // if the item is matched we are
                    // adding it to our filtered list.
                    filteredList.add(item)
                }
            }
            postMoveListFiltered(filteredList)
        } else {
            postMoveListFiltered(_moveOriginalList.value!!)
        }
    }

    fun getMoreMoves() {
        viewModelScope.launch {
            if (offset.value!! <= _total.value!!) {
                setOffset(offset.value!! + limit.value!!)
                getMoves()
            }
        }
    }

    fun setLimit(i: Int) {
        _limit.value = i
    }

    fun setOffset(i: Int) {
        _offset.value = i
    }

    private fun log(item: String) {
        viewModelScope.launch {
            logger.write(this::class.java.name, LoggerType.INFO, item)
        }
    }

}