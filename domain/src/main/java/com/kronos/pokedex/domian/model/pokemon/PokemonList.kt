package com.kronos.pokedex.domian.model.pokemon

import java.io.Serializable

data class PokemonList(
    var name:String = "",
    var url:String = "",
    var total:Int = 0,
):Serializable
