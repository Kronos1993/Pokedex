package com.kronos.webclient

data class Response<T>(
    var data:T?,
    var ex:Throwable?,
    var errors:List<String> = listOf(),
    var code:Int = 0
)
