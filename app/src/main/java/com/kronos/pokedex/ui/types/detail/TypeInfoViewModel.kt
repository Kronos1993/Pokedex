package com.kronos.pokedex.ui.types.detail

import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kronos.core.extensions.asLiveData
import com.kronos.core.view_model.ParentViewModel
import com.kronos.logger.interfaces.ILogger
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.pokemon.PokemonDexEntry
import com.kronos.pokedex.domian.model.type.TypeInfo
import com.kronos.pokedex.domian.repository.TypeRemoteRepository
import com.kronos.pokedex.ui.move.PokemonMoveListAdapter
import com.kronos.pokedex.ui.move.ShowMoveIn
import com.kronos.pokedex.ui.pokemon.list.PokemonListAdapter
import com.kronos.pokedex.util.preferences.PreferencesUtil
import com.kronos.webclient.UrlProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import javax.inject.Inject

@HiltViewModel
class TypeInfoViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    private var typeRemoteRepository: TypeRemoteRepository,
    var urlProvider: UrlProvider,
    var logger: ILogger,
) : ParentViewModel() {

    private val _typeInfo = MutableLiveData<TypeInfo>()
    val typeInfo = _typeInfo.asLiveData()

    var typeName = ObservableField<String?>()

    var doubleDamageFromAdapter: WeakReference<DamageRelationTypeAdapter?> = WeakReference(DamageRelationTypeAdapter())
    private val _doubleDamageFrom = MutableLiveData<List<DamageRelationContainer>>()
    val doubleDamageFrom = _doubleDamageFrom.asLiveData()

    var halfDamageFromAdapter: WeakReference<DamageRelationTypeAdapter?> = WeakReference(DamageRelationTypeAdapter())
    private val _halfDamageFrom = MutableLiveData<List<DamageRelationContainer>>()
    val halfDamageFrom = _halfDamageFrom.asLiveData()

    var noDamageFromAdapter: WeakReference<DamageRelationTypeAdapter?> = WeakReference(DamageRelationTypeAdapter())
    private val _noDamageFrom = MutableLiveData<List<DamageRelationContainer>>()
    val noDamageFrom = _noDamageFrom.asLiveData()


    var doubleDamageToAdapter: WeakReference<DamageRelationTypeAdapter?> = WeakReference(DamageRelationTypeAdapter())
    private val _doubleDamageTo = MutableLiveData<List<DamageRelationContainer>>()
    val doubleDamageTo = _doubleDamageTo.asLiveData()

    var halfDamageToAdapter: WeakReference<DamageRelationTypeAdapter?> = WeakReference(DamageRelationTypeAdapter())
    private val _halfDamageTo = MutableLiveData<List<DamageRelationContainer>>()
    val halfDamageTo = _halfDamageTo.asLiveData()

    var noDamageToAdapter: WeakReference<DamageRelationTypeAdapter?> = WeakReference(DamageRelationTypeAdapter())
    private val _noDamageTo = MutableLiveData<List<DamageRelationContainer>>()
    val noDamageTo = _noDamageTo.asLiveData()

    var pokemonListAdapter: WeakReference<PokemonListAdapter?> = WeakReference(PokemonListAdapter())
    private val _pokemonList = MutableLiveData<MutableList<PokemonDexEntry>>()
    val pokemonList = _pokemonList.asLiveData()

    var moveListAdapter: WeakReference<PokemonMoveListAdapter?> = WeakReference(PokemonMoveListAdapter(ShowMoveIn.MOVE_LIST))
    private val _moveList = MutableLiveData<MutableList<NamedResourceApi>>()
    val moveList = _moveList.asLiveData()

    private fun postTypeInfo(type: TypeInfo) {
        viewModelScope.run {
            _typeInfo.postValue(type)
        }
    }

    private fun postPokemonList(list: List<PokemonDexEntry>) {
        if (_pokemonList.value != null) {
            var pokelist = _pokemonList.value!!
            list.forEach {
                if (!(pokelist as ArrayList).contains(it)) {
                    pokelist.add(it)
                }
            }
            _pokemonList.postValue(pokelist as MutableList<PokemonDexEntry>?)
        } else {
            _pokemonList.postValue(list as MutableList<PokemonDexEntry>?)
        }
    }

    private fun postMoveList(list: List<NamedResourceApi>) {
        var moveList =  mutableListOf<NamedResourceApi>()
        if (_moveList.value != null) {
            moveList = _moveList.value!!
        }
        list.forEach {
            if (!(moveList as ArrayList).contains(it)) {
                moveList.add(it)
            }
        }
        _moveList.postValue(moveList as MutableList<NamedResourceApi>?)
    }

    fun loadTypeInfo(type: NamedResourceApi) {
        viewModelScope.launch(Dispatchers.IO) {
            loading.postValue(true)
            var typeInfo: TypeInfo? = null
            typeName.set("")

            typeInfo = if(!type.url.isNullOrEmpty()){
                if (urlProvider.extractIdFromUrl(type.url) != null) {
                    typeRemoteRepository.getTypeInfo(urlProvider.extractIdFromUrl(type.url))
                } else {
                    typeRemoteRepository.getTypeInfo(type.name)
                }
            }else{
                typeRemoteRepository.getTypeInfo(type.name)
            }
            postTypeInfo(typeInfo)

            _doubleDamageFrom.postValue(typeInfo.damageRelations.doubleDamageFrom.map { DamageRelationContainer(it.name,"x2") })
            _halfDamageFrom.postValue(typeInfo.damageRelations.halfDamageFrom.map { DamageRelationContainer(it.name,"x1/2") })
            _noDamageFrom.postValue(typeInfo.damageRelations.noDamageFrom.map { DamageRelationContainer(it.name,"x0") })

            _doubleDamageTo.postValue(typeInfo.damageRelations.doubleDamageTo.map { DamageRelationContainer(it.name,"x2") })
            _halfDamageTo.postValue(typeInfo.damageRelations.halfDamageTo.map { DamageRelationContainer(it.name,"x1/2") })
            _noDamageTo.postValue(typeInfo.damageRelations.noDamageTo.map { DamageRelationContainer(it.name,"x0") })

            postMoveList(typeInfo.moves)
            postPokemonList(typeInfo.pokemon.mapIndexed { index, namedResourceApi -> PokemonDexEntry(index,namedResourceApi) })
            loading.postValue(false)
        }
    }

    fun getTypeName(type: TypeInfo){
        if (type.names.isNotEmpty()) {
            var find = false
            var pos = 0
            while (!find && pos < type.names.size) {
                if (type.names[pos].language.name == PreferencesUtil.getLanguagePreference(
                        context
                    )
                ) {
                    typeName.set(type.names[pos].name)
                    find = true
                } else
                    pos++
            }
        } else
            typeName.set(type.name)
    }
}