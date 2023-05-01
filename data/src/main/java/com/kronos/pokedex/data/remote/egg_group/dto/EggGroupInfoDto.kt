package com.kronos.pokedex.data.remote.egg_group.dto

import com.google.gson.annotations.SerializedName
import com.kronos.pokedex.data.remote.NameDto
import com.kronos.pokedex.data.remote.response_list.NamedResourceApiDto
import com.kronos.pokedex.domian.model.Name
import com.kronos.pokedex.domian.model.NamedResourceApi
import java.io.Serializable

data class EggGroupInfoDto(
    @SerializedName("id")
    var id:Int = 0,
    @SerializedName("name")
    var name:String = "",
    @SerializedName("names")
    var names:List<NameDto> = listOf(),
    @SerializedName("pokemon_species")
    var pokemonSpecies:List<NamedResourceApiDto> = listOf(),
):Serializable
