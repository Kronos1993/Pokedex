package com.kronos.pokedex

import android.app.Application
import com.kronos.logger.interfaces.ILogger
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject


const val TAG = "PokedexApp"

@HiltAndroidApp
class PokedexApplication:Application(){

    @Inject
    lateinit var logger: ILogger

    override fun onCreate() {
        super.onCreate()
        logger.configure()
    }


}