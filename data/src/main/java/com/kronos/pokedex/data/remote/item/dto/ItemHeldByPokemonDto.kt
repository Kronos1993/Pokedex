package com.kronos.pokedex.data.remote.item.dto

import com.google.gson.annotations.SerializedName
import com.kronos.pokedex.data.remote.description.EffectEntryDto
import com.kronos.pokedex.data.remote.description.FlavorTextDto
import com.kronos.pokedex.data.remote.response_list.NamedResourceApiDto
import com.kronos.pokedex.data.remote.sprite.dto.SpriteDto
import com.kronos.pokedex.domian.model.EffectEntry
import com.kronos.pokedex.domian.model.FlavorText
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.ResourceApi
import com.kronos.pokedex.domian.model.sprite.Sprite
import java.io.Serializable

data class ItemHeldByPokemonDto(
    @SerializedName("pokemon")
    var pokemon:NamedResourceApiDto = NamedResourceApiDto(),
):Serializable
