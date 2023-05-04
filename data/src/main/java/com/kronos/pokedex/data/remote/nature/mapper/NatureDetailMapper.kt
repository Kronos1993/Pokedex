package com.kronos.pokedex.data.remote.nature.mapper

import com.kronos.pokedex.data.remote.nature.dto.NatureDetailDto
import com.kronos.pokedex.domian.model.Name
import com.kronos.pokedex.domian.model.nature.NatureDetail

fun NatureDetailDto.toNatureDetail(): NatureDetail =
    NatureDetail(
        name = name,
        names = names.map { Name(it.name,it.language) },
        decreasedStat = decreasedStat.let { it?.name },
        increasedStat = increasedStat.let { it?.name },
        hatesFlavor = hatesFlavor.let { it?.name },
        likesFlavor = likesFlavor.let { it?.name },
    )
