package com.kronos.webclient

import javax.inject.Inject

class UrlProviderImp @Inject constructor(
) : UrlProvider {
    override fun getApiUrl(): String {
        return UrlConstants.BASE_URL
    }

    override fun getServerUrl(): String {
        return UrlConstants.SERVER_URL
    }

    override fun getImageUrl(id:Int): String {
        return "${UrlConstants.IMAGE_URL + id}.png"
    }
}