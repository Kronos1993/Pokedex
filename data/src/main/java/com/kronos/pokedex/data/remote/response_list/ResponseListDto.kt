/*
 * Copyright (c) 2022. Kronos
 * Created by Marcos Octavio Guerra Liso
 */

package com.kronos.pokedex.data.remote.response_list

import com.google.gson.annotations.SerializedName

data class ResponseListDto<Any>(
    @SerializedName("count")
    var count:Int = 0,
    @SerializedName("next")
    var next:String = "",
    @SerializedName("results")
    var results:List<Any> = listOf(),
)
