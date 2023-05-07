package com.kronos.pokedex.util.preferences

import android.content.Context
import androidx.preference.PreferenceManager

class PreferencesUtil(){

    companion object{
        fun getLanguagePreference(context:Context) = PreferenceManager.getDefaultSharedPreferences(context).getString("app_language","en")
    }

}

