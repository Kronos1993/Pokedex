/*
 * Copyright (c) 2022. Kronos
 * Created by Marcos Octavio Guerra Liso
 */

package com.kronos.pokedex.domian.model.pokemon

import java.io.Serializable

//models the object from pokemon api ws

data class PokemonDexEntry(
    var dexEntry:Int = 0,
    var pokemon:PokemonList = PokemonList(),
):Serializable
