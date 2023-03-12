package com.kronos.pokedex.ui.move.list

import android.content.Context
import com.kronos.core.view_model.ParentViewModel
import com.kronos.logger.interfaces.ILogger
import com.kronos.pokedex.domian.repository.MoveRemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class MoveListViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    private var moveRemoteRepository: MoveRemoteRepository,
    var logger: ILogger,
) : ParentViewModel()  {


}