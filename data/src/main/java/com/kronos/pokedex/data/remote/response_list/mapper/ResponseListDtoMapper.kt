package com.kronos.pokedex.data.remote.response_list.mapper

import com.kronos.pokedex.data.remote.response_list.ResponseListDto
import com.kronos.pokedex.domian.model.ResponseList

fun ResponseListDto<Any>.toResponseList(): ResponseList<Any> =
    ResponseList(
        count = count,
        next = next,
        results = results
    )
