package com.kronos.pokedex.domian.model.ability

import com.kronos.pokedex.domian.model.NamedResourceApi
import java.io.Serializable

data class PokemonWithAbility(
    val pokemon: NamedResourceApi = NamedResourceApi(),
    val isHidden:Boolean = true,
):Serializable
