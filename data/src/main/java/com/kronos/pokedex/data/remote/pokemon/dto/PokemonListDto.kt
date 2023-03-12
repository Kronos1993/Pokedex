/*
 * Copyright (c) 2022. Kronos
 * Created by Marcos Octavio Guerra Liso
 */

package com.kronos.pokedex.data.remote.pokemon.dto

import com.google.gson.annotations.SerializedName

//models the object from pokemon api ws

data class PokemonListDto(
    @SerializedName("name")
    var name:String = "",
    @SerializedName("url")
    var url:String = "",
)
