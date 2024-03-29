package com.kronos.pokedex.data.remote.ability.mapper

import com.kronos.pokedex.data.remote.ability.dto.AbilityDto
import com.kronos.pokedex.data.remote.ability.dto.AbilityInfoDto
import com.kronos.pokedex.data.remote.ability.dto.PokemonWithAbilityDto
import com.kronos.pokedex.data.remote.description.mapper.toEffectEntry
import com.kronos.pokedex.data.remote.description.mapper.toFlavorText
import com.kronos.pokedex.data.remote.response_list.mapper.toNamedResource
import com.kronos.pokedex.domian.model.Name
import com.kronos.pokedex.domian.model.ability.Ability
import com.kronos.pokedex.domian.model.ability.AbilityInfo
import com.kronos.pokedex.domian.model.ability.PokemonWithAbility

fun AbilityDto.toAbility(): Ability =
    Ability(
        ability = ability.toNamedResource(),
        isHidden = isHidden,
    )


fun AbilityInfoDto.toAbilityInfo(): AbilityInfo =
    AbilityInfo(
        id = id,
        name = name,
        names = names.map { Name(it.name,it.language) },
        pokemon = pokemon.map { it.toPokemonWithAbility() },
        flavorText = flavorTextEntryEntries.map { it.toFlavorText() },
        effects = effectEntries.map { it.toEffectEntry() },
    )


fun PokemonWithAbilityDto.toPokemonWithAbility(): PokemonWithAbility =
    PokemonWithAbility(
        pokemon = pokemon.toNamedResource(),
        isHidden = isHidden,
    )