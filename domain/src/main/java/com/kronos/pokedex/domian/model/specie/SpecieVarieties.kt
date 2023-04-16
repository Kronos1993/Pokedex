package com.kronos.pokedex.domian.model.specie

import com.kronos.pokedex.domian.model.NamedResourceApi
import java.io.Serializable

data class SpecieVarieties(
    var isDefault: Boolean = false,
    var pokemon: NamedResourceApi = NamedResourceApi(),
):Serializable
