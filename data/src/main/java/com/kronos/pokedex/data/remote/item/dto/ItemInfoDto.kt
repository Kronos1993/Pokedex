package com.kronos.pokedex.data.remote.item.dto

import com.google.gson.annotations.SerializedName
import com.kronos.pokedex.data.remote.description.EffectEntryDto
import com.kronos.pokedex.data.remote.description.FlavorTextDto
import com.kronos.pokedex.data.remote.response_list.NamedResourceApiDto
import com.kronos.pokedex.data.remote.sprite.dto.SpriteDto
import com.kronos.pokedex.domian.model.ResourceApi
import java.io.Serializable

data class ItemInfoDto(
    @SerializedName("attributes")
    var attributes:List<NamedResourceApiDto> = listOf(),
    @SerializedName("baby_trigger_for")
    var babyTriggerFor:ResourceApi=ResourceApi(),
    @SerializedName("category")
    var category:NamedResourceApiDto = NamedResourceApiDto(),
    @SerializedName("cost")
    var cost:Int = 0,
    @SerializedName("effect_entries")
    var effectEntries:List<EffectEntryDto> = listOf(),
    @SerializedName("flavor_text_entries")
    var descriptions:List<FlavorTextDto> = listOf(),
    @SerializedName("fling_effect")
    var flingEffect:NamedResourceApiDto = NamedResourceApiDto(),
    @SerializedName("fling_power")
    var flingPower:Int = 0,
    @SerializedName("held_by_pokemon")
    var heldByPokemon:List<ItemHeldByPokemonDto> = listOf(),
    @SerializedName("id")
    var id:Int = 0,
    @SerializedName("name")
    var name:String = "",
    @SerializedName("sprites")
    var sprites:SpriteDto = SpriteDto()
):Serializable
