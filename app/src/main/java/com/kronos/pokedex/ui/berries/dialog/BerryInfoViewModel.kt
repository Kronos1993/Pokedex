package com.kronos.pokedex.ui.berries.dialog

import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kronos.core.extensions.asLiveData
import com.kronos.core.view_model.ParentViewModel
import com.kronos.logger.interfaces.ILogger
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.item.BerryInfo
import com.kronos.pokedex.domian.repository.BerryRemoteRepository
import com.kronos.pokedex.util.preferences.PreferencesUtil
import com.kronos.webclient.UrlProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import javax.inject.Inject

@HiltViewModel
class BerryInfoViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    private var berryRemoteRepository: BerryRemoteRepository,
    var urlProvider: UrlProvider,
    var logger: ILogger,
) : ParentViewModel() {

    private val _berryInfo = MutableLiveData<BerryInfo>()
    val berryInfo = _berryInfo.asLiveData()

    var berryName = ObservableField<String?>()

    var berryFlavorListAdapter: WeakReference<BerryFlavorListAdapter?> = WeakReference(BerryFlavorListAdapter())

    fun postBerryInfo(berryInfo: BerryInfo) {
        _berryInfo.postValue(berryInfo)
    }

    fun loadBerryInfo(berry: NamedResourceApi) {
        viewModelScope.launch(Dispatchers.IO) {
            loading.postValue(true)
            var berryInfo: BerryInfo? = null

            berryInfo = if (urlProvider.extractIdFromUrl(berry.url) != null) {
                berryRemoteRepository.getBerry(urlProvider.extractIdFromUrl(berry.url))
            } else {
                berryRemoteRepository.getBerry((berry.name))
            }
            postBerryInfo(berryInfo)
            loading.postValue(false)
        }
    }

    fun getBerryName(berry:BerryInfo){
        if (berry.names.isNotEmpty()) {
            var find = false
            var pos = 0
            while (!find && pos < berry.names.size) {
                if (berry.names[pos].language.name == PreferencesUtil.getLanguagePreference(
                        context
                    )
                ) {
                    berryName.set(berry.names[pos].name)
                    find = true
                } else
                    pos++
            }
        } else
            berryName.set(berry.name)
    }

}