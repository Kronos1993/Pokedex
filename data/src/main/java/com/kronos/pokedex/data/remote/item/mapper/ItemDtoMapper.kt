package com.kronos.pokedex.data.remote.item.mapper

import com.kronos.pokedex.data.remote.description.mapper.toEffectEntry
import com.kronos.pokedex.data.remote.description.mapper.toFlavorText
import com.kronos.pokedex.data.remote.item.dto.ItemCategoryDto
import com.kronos.pokedex.data.remote.item.dto.ItemInfoDto
import com.kronos.pokedex.data.remote.response_list.mapper.toNamedResource
import com.kronos.pokedex.data.remote.sprite.mapper.toSprite
import com.kronos.pokedex.domian.model.Name
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.ResourceApi
import com.kronos.pokedex.domian.model.item.ItemCategory
import com.kronos.pokedex.domian.model.item.ItemInfo

fun ItemInfoDto.toItemInfo(): ItemInfo =
    ItemInfo(
        name = name,
        names = names.map { Name(it.name,it.language) },
        id = id,
        attributes = attributes.map { it.toNamedResource() },
        sprites = sprites.toSprite(),
        babyTriggerFor = babyTriggerFor.let { if (it != null) it else ResourceApi() },
        category = category.toNamedResource(),
        cost = cost,
        descriptions = descriptions.map { it.toFlavorText() },
        effectEntries = effectEntries.map { it.toEffectEntry() },
        flingEffect = flingEffect?.toNamedResource() ?: NamedResourceApi(),
        flingPower = flingPower,
        heldByPokemon = heldByPokemon.map { it.pokemon.toNamedResource() }
    )

fun ItemCategoryDto.toItemCategory(): ItemCategory =
    ItemCategory(
        id = id,
        name = name,
        items = items.map { it.toNamedResource() },
        pocket = pocket.toNamedResource()
    )
