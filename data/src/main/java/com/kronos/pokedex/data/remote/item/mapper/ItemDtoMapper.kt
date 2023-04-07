package com.kronos.pokedex.data.remote.item.mapper

import com.kronos.pokedex.data.remote.description.mapper.toEffectEntry
import com.kronos.pokedex.data.remote.description.mapper.toFlavorText
import com.kronos.pokedex.data.remote.item.dto.ItemInfoDto
import com.kronos.pokedex.data.remote.response_list.mapper.toNamedResource
import com.kronos.pokedex.data.remote.sprite.mapper.toSprite
import com.kronos.pokedex.domian.model.item.ItemInfo

fun ItemInfoDto.toItemInfo(): ItemInfo =
    ItemInfo(
        name = name,
        id = id,
        attributes = attributes.map { it.toNamedResource() },
        sprites = sprites.toSprite(),
        babyTriggerFor = babyTriggerFor,
        category = category.toNamedResource(),
        cost = cost,
        descriptions = descriptions.map { it.toFlavorText() },
        effectEntries = effectEntries.map { it.toEffectEntry() },
        flingEffect = flingEffect.toNamedResource(),
        flingPower = flingPower,
        heldByPokemon = heldByPokemon.map { it.pokemon.toNamedResource() }
    )
