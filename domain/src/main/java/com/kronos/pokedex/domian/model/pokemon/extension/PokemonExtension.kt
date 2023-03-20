package com.kronos.pokedex.domian.model.pokemon.extension

import com.kronos.pokedex.domian.model.pokemon.PokemonInfo

fun PokemonInfo.totalStat():Int{
    var total = 0
    this.stats.forEach { total += it.baseStat}
    return total
}