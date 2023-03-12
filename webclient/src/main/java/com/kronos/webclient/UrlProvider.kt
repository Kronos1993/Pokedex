package com.kronos.webclient

interface UrlProvider {
    fun getApiUrl():String
    fun getServerUrl():String
    fun getImageUrl(id:Int):String
}