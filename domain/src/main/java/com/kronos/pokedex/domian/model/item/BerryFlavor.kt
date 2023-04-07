package com.kronos.pokedex.domian.model.item

import com.kronos.pokedex.domian.model.NamedResourceApi
import java.io.Serializable

data class BerryFlavor(
    var flavor: NamedResourceApi = NamedResourceApi(),
    var potency:Int = 0,
): Serializable