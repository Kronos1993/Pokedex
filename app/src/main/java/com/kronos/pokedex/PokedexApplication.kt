package com.kronos.pokedex

import android.app.Application
import com.kronos.logger.exception.ExceptionHandler
import com.kronos.logger.interfaces.ILogger
import dagger.hilt.android.HiltAndroidApp
import java.lang.Exception
import javax.inject.Inject


const val TAG = "PokedexApp"

@HiltAndroidApp
class PokedexApplication:Application(){

    @Inject
    lateinit var logger: ILogger
    @Inject
    lateinit var exceptionHandler: ExceptionHandler

    override fun onCreate() {
        super.onCreate()
        try {
            exceptionHandler.init(this)
            logger.configure()
        }catch (e:Exception){
        }

    }
}