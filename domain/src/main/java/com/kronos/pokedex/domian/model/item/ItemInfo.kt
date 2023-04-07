package com.kronos.pokedex.domian.model.item

import com.kronos.pokedex.domian.model.EffectEntry
import com.kronos.pokedex.domian.model.FlavorText
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.ResourceApi
import com.kronos.pokedex.domian.model.sprite.Sprite
import java.io.Serializable

data class ItemInfo(
    var attributes:List<NamedResourceApi> = listOf(),
    var babyTriggerFor:ResourceApi=ResourceApi(),
    var category:NamedResourceApi = NamedResourceApi(),
    var cost:Int = 0,
    var effectEntries:List<EffectEntry> = listOf(),
    var descriptions:List<FlavorText> = listOf(),
    var flingEffect:NamedResourceApi = NamedResourceApi(),
    var flingPower:Int = 0,
    var heldByPokemon:List<NamedResourceApi> = listOf(),
    var id:Int = 0,
    var name:String = "",
    var sprites:Sprite = Sprite()
):Serializable
