package com.kronos.pokedex.data.remote.response_list.mapper

import com.kronos.pokedex.data.remote.response_list.NamedResourceApiDto
import com.kronos.pokedex.domian.model.NamedResourceApi

fun NamedResourceApiDto.toNamedResource(): NamedResourceApi =
    NamedResourceApi(
        name = name,
        url = url
    )
