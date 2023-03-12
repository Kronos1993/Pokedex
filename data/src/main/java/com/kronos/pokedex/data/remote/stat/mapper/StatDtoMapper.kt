package com.kronos.pokedex.data.remote.stat.mapper

import com.kronos.pokedex.data.remote.stat.dto.StatDto
import com.kronos.pokedex.domian.model.stat.Stat

fun StatDto.toStat(): Stat =
    Stat(
        baseStat = baseStat,
        statName = statDto.name,
        statEffort = effort
    )
