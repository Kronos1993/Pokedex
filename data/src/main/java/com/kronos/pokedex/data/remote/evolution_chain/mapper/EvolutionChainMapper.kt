package com.kronos.pokedex.data.remote.evolution_chain.mapper

import com.kronos.pokedex.data.remote.evolution_chain.dto.ChainLinkDto
import com.kronos.pokedex.data.remote.evolution_chain.dto.EvolutionChainDto
import com.kronos.pokedex.data.remote.evolution_chain.dto.EvolutionDetailDto
import com.kronos.pokedex.data.remote.evolution_chain.dto.EvolutionTriggerDto
import com.kronos.pokedex.data.remote.response_list.mapper.toNamedResource
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.evolution_chain.ChainLink
import com.kronos.pokedex.domian.model.evolution_chain.EvolutionChain
import com.kronos.pokedex.domian.model.evolution_chain.EvolutionDetail
import com.kronos.pokedex.domian.model.evolution_chain.EvolutionTrigger

fun ChainLinkDto.toChainLink(): ChainLink =
    ChainLink(
        evolutionDetails = evolutionDetails?.let {
            it.map{it.toEvolutionDetail()}
        } ?: listOf(),
        evolvesTo = evolvesTo?.let {
            it.map{it.toChainLink()}
        }?: listOf(),
        isBaby = isBaby,
        species = species?.let{
            it.toNamedResource()
        }?:NamedResourceApi()
    )

fun EvolutionChainDto.toEvolutionChain(): EvolutionChain =
    EvolutionChain(
        id = id,
        babyFriggerItem = babyFriggerItem?.let{
            it.toNamedResource()
        }?:NamedResourceApi(),
        chain = chain?.let{
            it.toChainLink()
        },
    )

fun EvolutionDetailDto.toEvolutionDetail(): EvolutionDetail =
    EvolutionDetail(
        trigger = trigger?.let{
            it.toNamedResource()
        }?:NamedResourceApi(),
        item = item?.let{
            it.toNamedResource()
        }?:NamedResourceApi(),
        gender = gender,
        heldItem = heldItem?.let{
            it.toNamedResource()
        }?:NamedResourceApi(),
        knownMove = knownMove?.let{
            it.toNamedResource()
        }?:NamedResourceApi(),
        knownMoveType = knownMoveType?.let{
            it.toNamedResource()
        }?:NamedResourceApi(),
        location = location?.let{
            it.toNamedResource()
        }?:NamedResourceApi(),
        minLevel = minLevel,
        minHappiness = minHappiness,
        minBeauty = minBeauty,
        minAffection = minAffection,
        partySpecies = partySpecies?.let{
            it.toNamedResource()
        }?:NamedResourceApi(),
        partyType = partyType?.let{
            it.toNamedResource()
        }?:NamedResourceApi(),
        relativePhysicalStats = relativePhysicalStats,
        timeOfDay = timeOfDay,
        tradeSpecies = tradeSpecies?.let{
            it.toNamedResource()
        }?:NamedResourceApi(),
        needsOverworldRain = needsOverworldRain,
        turnUpsideDown = turnUpsideDown
    )

fun EvolutionTriggerDto.toEvolutionTrigger(): EvolutionTrigger =
    EvolutionTrigger(
        id = id,
        name = name,
        pokemonSpecies = pokemonSpecies.map {
            it.toNamedResource()
        }
    )