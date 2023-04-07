package com.kronos.webclient

interface UrlProvider {
    fun getApiUrl():String
    fun getServerUrl():String
    fun getPokemonImageUrl(id:Int):String
    fun getItemImageUrl(item:String):String

    fun extractIdFromUrl(url:String):Int
}