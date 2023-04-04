package com.kronos.pokedex.data.data_source.nature

import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.ResponseList
import com.kronos.pokedex.domian.model.nature.NatureDetail

interface NatureRemoteDataSource {

    suspend fun listNature(limit:Int = 20,offset:Int = 0): ResponseList<NamedResourceApi>

    suspend fun getNature(natureId: Int = 1): NatureDetail

    suspend fun getNature(nature: String): NatureDetail

}