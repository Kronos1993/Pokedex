package com.kronos.pokedex.data.remote.ability.dto

import com.google.gson.annotations.SerializedName
import com.kronos.pokedex.data.remote.Effect
import com.kronos.pokedex.data.remote.response_list.NamedResourceApiDto

data class AbilityEffectChangeDto(
    @SerializedName("effect_entries")
    val effectEntries: List<Effect>,
    @SerializedName("version_group")
    val versionGroup: NamedResourceApiDto
)
