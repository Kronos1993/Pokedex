package com.kronos.pokedex.domian.model.item

import com.kronos.pokedex.domian.model.NamedResourceApi
import java.io.Serializable

data class BerryInfo(
    var firmness:NamedResourceApi = NamedResourceApi(),
    var flavors:List<BerryFlavor> = listOf(),
    var growthTime:Int = 0,
    var id:Int = 0,
    var itemResource:NamedResourceApi = NamedResourceApi(),
    var item:ItemInfo = ItemInfo(),
    var maxHarvest:Int = 0,
    var name:String = "",
    var naturalGiftPower:Int = 0,
    var naturalGiftType:NamedResourceApi = NamedResourceApi(),
    var size:Int = 0,
    var smoothness:Int = 0,
    var soilDryness:Int = 0
):Serializable
