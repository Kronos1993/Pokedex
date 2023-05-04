package com.kronos.pokedex.data.remote.type.mapper

import com.kronos.pokedex.data.remote.response_list.mapper.toNamedResource
import com.kronos.pokedex.data.remote.type.dto.DamageRelationDto
import com.kronos.pokedex.data.remote.type.dto.TypeDto
import com.kronos.pokedex.data.remote.type.dto.TypeInfoDto
import com.kronos.pokedex.domian.model.Name
import com.kronos.pokedex.domian.model.type.DamageRelation
import com.kronos.pokedex.domian.model.type.Type
import com.kronos.pokedex.domian.model.type.TypeInfo

fun TypeDto.toType(): Type =
    Type(
        slot = slot,
        name = type.name
    )

fun DamageRelationDto.toDamageRelation(): DamageRelation =
    DamageRelation(
        doubleDamageFrom = doubleDamageFrom.map { it.toNamedResource() },
        doubleDamageTo = doubleDamageTo.map { it.toNamedResource() },
        halfDamageFrom = halfDamageFrom.map { it.toNamedResource() },
        halfDamageTo = halfDamageTo.map { it.toNamedResource() },
        noDamageFrom = noDamageFrom.map { it.toNamedResource() },
        noDamageTo = noDamageTo.map { it.toNamedResource() },
    )

fun TypeInfoDto.toTypeInfo(): TypeInfo =
    TypeInfo(
        name = name,
        names = names.map { Name(it.name,it.language) },
        id = id,
        damageRelations = damageRelations.toDamageRelation(),
        moves = moves.map { it.toNamedResource() },
        pokemon = pokemon.map { it.toNamedResource() }
    )
