package com.kronos.pokedex.domian.model.item

import com.kronos.pokedex.domian.model.NamedResourceApi
import java.io.Serializable

data class ItemCategory(
    var id:Int = 0,
    var items:List<NamedResourceApi> = listOf(),
    var name: String = "",
    var pocket: NamedResourceApi = NamedResourceApi()
):Serializable