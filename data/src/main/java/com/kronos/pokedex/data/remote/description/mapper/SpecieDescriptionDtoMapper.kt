package com.kronos.pokedex.data.remote.description.mapper

import com.kronos.pokedex.data.remote.description.DescriptionDto
import com.kronos.pokedex.domian.model.description.Description

fun DescriptionDto.toDescription(): Description =
    Description(
        description = description,
        language = language.name
    )