package com.kronos.pokedex.ui.nature.dialog

import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kronos.core.extensions.asLiveData
import com.kronos.core.view_model.ParentViewModel
import com.kronos.logger.interfaces.ILogger
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.egg_group.EggGroupInfo
import com.kronos.pokedex.domian.model.nature.NatureDetail
import com.kronos.pokedex.domian.repository.NatureRemoteRepository
import com.kronos.pokedex.util.preferences.PreferencesUtil
import com.kronos.webclient.UrlProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NatureDetailViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    private var natureRemoteRepository: NatureRemoteRepository,
    var urlProvider: UrlProvider,
    var logger: ILogger,
) : ParentViewModel() {

    private val _natureInfo = MutableLiveData<NatureDetail>()
    val natureInfo = _natureInfo.asLiveData()

    var natureName = ObservableField<String?>()

    fun postNatureInfo(natureInfo: NatureDetail) {
        _natureInfo.postValue(natureInfo)
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

    fun getNatureName(nature: NatureDetail){
        if (nature.names.isNotEmpty()) {
            var find = false
            var pos = 0
            while (!find && pos < nature.names.size) {
                if (nature.names[pos].language.name == PreferencesUtil.getLanguagePreference(
                        context
                    )
                ) {
                    natureName.set(nature.names[pos].name)
                    find = true
                } else
                    pos++
            }
        } else
            natureName.set(nature.name)
    }
}