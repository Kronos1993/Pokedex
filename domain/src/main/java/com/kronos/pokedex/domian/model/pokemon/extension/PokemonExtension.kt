package com.kronos.pokedex.domian.model.pokemon.extension

import com.kronos.pokedex.domian.model.pokemon.PokemonInfo

fun PokemonInfo.totalStat():Int{
    var total = 0
    this.stats.forEach { total += it.baseStat}
    return total
}

fun PokemonInfo.maxStatValue():Int{
    var stat = this.stats.maxByOrNull { it.baseStat }
    return stat.let {
        if (it!=null)
            it.baseStat!! + 10
        else
            0
    }
}