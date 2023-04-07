package com.kronos.pokedex.data.remote.ability.dto

import com.google.gson.annotations.SerializedName
import com.kronos.pokedex.data.remote.Name
import com.kronos.pokedex.data.remote.description.EffectEntryDto
import com.kronos.pokedex.data.remote.description.FlavorTextEntryDto
import com.kronos.pokedex.domian.model.NamedResourceApi

data class AbilityInfoDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("is_main_series")
    val isMainSeries: Boolean,
    @SerializedName("generation")
    val generation: NamedResourceApi,
    @SerializedName("names")
    val names: List<Name>,
    @SerializedName("effect_entries")
    val effectEntries: List<EffectEntryDto>,
    @SerializedName("effect_changes")
    val effectChanges: List<AbilityEffectChangeDto>,
    @SerializedName("flavor_text_entries")
    val flavorTextEntryEntries: List<FlavorTextEntryDto>,
    @SerializedName("pokemon")
    val pokemon: List<PokemonWithAbilityDto>
)
