package com.kronos.pokedex.domian.model

import java.io.Serializable


data class Name(
    val name: String = "",
    val language: NamedResourceApi = NamedResourceApi()
): Serializable
