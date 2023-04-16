package com.kronos.pokedex.data.remote.berry.mapper

import com.kronos.pokedex.data.remote.berry.dto.BerryFlavorDto
import com.kronos.pokedex.data.remote.berry.dto.BerryInfoDto
import com.kronos.pokedex.data.remote.response_list.mapper.toNamedResource
import com.kronos.pokedex.domian.model.item.BerryFlavor
import com.kronos.pokedex.domian.model.item.BerryInfo

fun BerryInfoDto.toBerryInfo(): BerryInfo =
    BerryInfo(
        name = name,
        id = id,
        size = size,
        firmness = firmness.toNamedResource(),
        flavors = flavors.map { it.toBerryFlavor() },
        growthTime = growthTime,
        itemResource = itemResource.toNamedResource(),
        maxHarvest = maxHarvest,
        naturalGiftPower = naturalGiftPower,
        naturalGiftType = naturalGiftType.toNamedResource(),
        smoothness = smoothness,
        soilDryness = soilDryness
    )


fun BerryFlavorDto.toBerryFlavor(): BerryFlavor =
    BerryFlavor(
        flavor = flavor.toNamedResource(),
        potency = potency
    )
