package com.kronos.pokedex.data.remote

import com.kronos.pokedex.domian.model.NamedResourceApi

data class Effect(
    val effect: String,
    val language: NamedResourceApi
)

data class NameDto(
    val name: String,
    val language: NamedResourceApi
)
