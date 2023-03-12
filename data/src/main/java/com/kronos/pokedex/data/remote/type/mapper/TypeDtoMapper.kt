package com.kronos.pokedex.data.remote.type.mapper

import com.kronos.pokedex.data.remote.type.dto.TypeDto
import com.kronos.pokedex.domian.model.type.Type

fun TypeDto.toType(): Type =
    Type(
        slot = slot,
        name = typeInfoDto.name
    )
