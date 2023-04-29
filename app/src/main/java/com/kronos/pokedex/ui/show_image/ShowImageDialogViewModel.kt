package com.kronos.pokedex.ui.show_image

import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kronos.core.extensions.asLiveData
import com.kronos.core.view_model.ParentViewModel
import com.kronos.logger.interfaces.ILogger
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.ability.AbilityInfo
import com.kronos.pokedex.domian.model.pokemon.PokemonDexEntry
import com.kronos.pokedex.domian.repository.AbilityRemoteRepository
import com.kronos.pokedex.ui.abilities.ShowAbilityIn
import com.kronos.pokedex.ui.move.ShowMoveIn
import com.kronos.pokedex.ui.pokemon.list.PokemonListAdapter
import com.kronos.webclient.UrlProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import javax.inject.Inject

@HiltViewModel
class ShowImageDialogViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    var urlProvider: UrlProvider,
    var logger: ILogger,
) : ParentViewModel() {

    private val _imageUrl = MutableLiveData<String>()
    val imageUrl = _imageUrl.asLiveData()

    fun postImageUrl(imageUrl: String) {
        _imageUrl.postValue(imageUrl)
    }
}