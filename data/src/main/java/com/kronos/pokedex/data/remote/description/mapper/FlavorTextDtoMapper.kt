package com.kronos.pokedex.data.remote.description.mapper

import com.kronos.pokedex.data.remote.description.EffectEntryDto
import com.kronos.pokedex.data.remote.description.FlavorTextDto
import com.kronos.pokedex.data.remote.description.FlavorTextEntryDto
import com.kronos.pokedex.domian.model.EffectEntry
import com.kronos.pokedex.domian.model.FlavorText

fun FlavorTextEntryDto.toFlavorText(): FlavorText =
    FlavorText(
        description = flavorText,
        language = language.name
    )

fun FlavorTextDto.toFlavorText(): FlavorText =
    FlavorText(
        description = flavorText,
        language = language.name
    )

fun EffectEntryDto.toEffectEntry(): EffectEntry =
    EffectEntry(
        effect = effect,
        shortEffect = shortEffect,
        language = language.name
    )