package com.kronos.pokedex.data.remote.ability.dto

import com.kronos.pokedex.data.remote.Name
import com.kronos.pokedex.data.remote.VerboseEffect
import com.kronos.pokedex.domian.model.NamedResourceApi

data class AbilityInfoDto(
    val id: Int,
    val name: String,
    val isMainSeries: Boolean,
    val generation: NamedResourceApi,
    val names: List<Name>,
    val effectEntries: List<VerboseEffect>,
    val effectChanges: List<AbilityEffectChangeDto>,
    val flavorTextEntries: List<AbilityFlavorTextDto>,
    val pokemon: List<AbilityDto>
)
