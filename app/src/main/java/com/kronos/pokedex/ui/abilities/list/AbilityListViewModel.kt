package com.kronos.pokedex.ui.abilities.list

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kronos.core.extensions.asLiveData
import com.kronos.core.view_model.ParentViewModel
import com.kronos.logger.LoggerType
import com.kronos.logger.interfaces.ILogger
import com.kronos.pokedex.domian.model.ability.Ability
import com.kronos.pokedex.domian.repository.AbilityRemoteRepository
import com.kronos.pokedex.ui.abilities.PokemonAbilityAdapter
import com.kronos.webclient.UrlProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import javax.inject.Inject

@HiltViewModel
class AbilityListViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    private var abilityRemoteRepository: AbilityRemoteRepository,
    var urlProvider: UrlProvider,
    var logger: ILogger,
) : ParentViewModel() {

    private val _abilityList = MutableLiveData<MutableList<Ability>>()
    val abilityList = _abilityList.asLiveData()

    private val _abilityOriginalList = MutableLiveData<MutableList<Ability>>()
    val abilityOriginalList = _abilityOriginalList.asLiveData()

    private val _limit = MutableLiveData<Int>()
    val limit = _limit.asLiveData()

    private val _offset = MutableLiveData<Int>()
    val offset = _offset.asLiveData()

    private val _recyclerLastPos = MutableLiveData<Int>()
    val recyclerLastPos = _recyclerLastPos.asLiveData()

    private var _total = MutableLiveData<Int>()
    val total = _total.asLiveData()

    var abilityAdapter: WeakReference<PokemonAbilityAdapter?> =
        WeakReference(PokemonAbilityAdapter())

    private fun postAbilityList(list: List<Ability>) {
        if (_abilityList.value != null) {
            var abilityList = _abilityList.value!!
            list.forEach {
                if (!(abilityList as ArrayList).contains(it)) {
                    abilityList.add(it)
                }
            }
            _abilityList.postValue(abilityList as MutableList<Ability>?)
        } else {
            _abilityList.postValue(list as MutableList<Ability>?)
        }
        loading.postValue(false)
    }

    private fun postAbilityListFiltered(list: List<Ability>) {
        _abilityList.postValue(list as MutableList<Ability>?)
    }

    private fun postOriginalAbilityList(list: List<Ability>) {
        if (_abilityOriginalList.value != null) {
            var abilityList = _abilityOriginalList.value!!
            list.forEach {
                if (!(abilityList as ArrayList).contains(it)) {
                    abilityList.add(it)
                }
            }
            _abilityOriginalList.postValue(abilityList as MutableList<Ability>?)
        } else {
            _abilityOriginalList.postValue(list as MutableList<Ability>?)
        }
    }

    fun getAbilities() {
        loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            var call = async {
                val responseList = abilityRemoteRepository.list(limit.value!!,offset.value!!)
                _total.postValue(responseList.count)
                postOriginalAbilityList(responseList.results.map{ Ability(it, false) })
                postAbilityList(responseList.results.map{ Ability(it, false) })
            }
            call.await()
        }
    }

    fun filterAbility(abilityName: String) {
        if (abilityName.isNotEmpty()) {
            // creating a new array list to filter our data.
            val filteredList: ArrayList<Ability> = ArrayList()
            // running a for loop to compare elements.
            for (item in _abilityOriginalList.value!!) {
                // checking if the entered string matched with any item of our recycler view.
                if (item.ability.name.lowercase().contains(abilityName.lowercase())) {
                    // if the item is matched we are
                    // adding it to our filtered list.
                    filteredList.add(item)
                }
            }
            postAbilityListFiltered(filteredList)
        } else {
            postAbilityListFiltered(_abilityOriginalList.value!!)
        }
    }

    fun getMoreAbilities() {
        viewModelScope.launch {
            if (offset.value!! <= _total.value!!) {
                setOffset(offset.value!! + limit.value!!)
                getAbilities()
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

    fun setRecyclerLastPosition(i: Int) {
        _recyclerLastPos.value = i
    }

}