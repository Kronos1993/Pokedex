package com.kronos.pokedex.data.remote.response_list.mapper

import com.kronos.pokedex.data.remote.response_list.ResponseListItemDto
import com.kronos.pokedex.domian.model.ResponseListItem

fun ResponseListItemDto.toResponseListItem(): ResponseListItem =
    ResponseListItem(
        name = name,
        url = url
    )
