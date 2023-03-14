package com.kronos.pokedex.data.remote.response_list

import com.google.gson.annotations.SerializedName

data class ResponseListItemDto(
    @SerializedName("name")
    var name:String = "",
    @SerializedName("url")
    var url:String = "",
)