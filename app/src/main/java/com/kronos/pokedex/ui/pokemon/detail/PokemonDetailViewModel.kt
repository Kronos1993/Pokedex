package com.kronos.pokedex.ui.pokemon.detail

import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kronos.core.extensions.asLiveData
import com.kronos.core.view_model.ParentViewModel
import com.kronos.logger.interfaces.ILogger
import com.kronos.pokedex.R
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.domian.model.ability.AbilityInfo
import com.kronos.pokedex.domian.model.evolution_chain.ChainLink
import com.kronos.pokedex.domian.model.evolution_chain.EvolutionChain
import com.kronos.pokedex.domian.model.move.MoveInfo
import com.kronos.pokedex.domian.model.move.MoveList
import com.kronos.pokedex.domian.model.pokemon.PokemonInfo
import com.kronos.pokedex.domian.model.pokemon.extension.totalStat
import com.kronos.pokedex.domian.model.specie.SpecieInfo
import com.kronos.pokedex.domian.model.stat.Stat
import com.kronos.pokedex.domian.repository.*
import com.kronos.pokedex.ui.abilities.PokemonAbilityAdapter
import com.kronos.pokedex.ui.move.PokemonMoveListAdapter
import com.kronos.pokedex.ui.pokemon.detail.adapter.PokemonEvolutionChainAdapter
import com.kronos.pokedex.ui.pokemon.detail.adapter.PokemonInfoPageAdapter
import com.kronos.pokedex.ui.pokemon.detail.adapter.PokemonSpriteAdapter
import com.kronos.pokedex.ui.stats.PokemonStatsAdapter
import com.kronos.pokedex.ui.types.PokemonTypeAdapter
import com.kronos.webclient.UrlProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private var pokemonRemoteRepository: PokemonRemoteRepository,
    private var pokemonSpecieRemoteRepository: SpecieRemoteRepository,
    private var pokemonEvolutionChainRemoteRepository: EvolutionChainRemoteRepository,
    private var abilityRemoteRepository: AbilityRemoteRepository,
    private var moveRemoteRepository: MoveRemoteRepository,
    var urlProvider: UrlProvider,
    var logger: ILogger,
) : ParentViewModel() {

    private val _pokemonInfo = MutableLiveData<PokemonInfo>()
    val pokemonInfo = _pokemonInfo.asLiveData()

    private val _pokemonStats = MutableLiveData<List<Stat>>()
    val pokemonStats = _pokemonStats.asLiveData()

    private val _pokemonMoves = MutableLiveData<List<MoveList>>()
    val pokemonMoves = _pokemonMoves.asLiveData()

    private val _pokemonEvolutionChain = MutableLiveData<EvolutionChain>()

    private val _pokemonEvolutionList = MutableLiveData<List<ChainLink>>()
    val pokemonEvolutionList = _pokemonEvolutionList.asLiveData()

    private val _pokemonSpritesUrl = MutableLiveData<List<Pair<String, String>>>()
    val pokemonSpritesUrl = _pokemonSpritesUrl.asLiveData()

    private val _pokemonOtherFormsUrl = MutableLiveData<List<Pair<String, String>>>()
    val pokemonOtherFormsUrl = _pokemonOtherFormsUrl.asLiveData()

    private val _abilityInfo = MutableLiveData<AbilityInfo>()
    val abilityInfo = _abilityInfo.asLiveData()

    private val _moveInfo = MutableLiveData<MoveInfo>()
    val moveInfo = _moveInfo.asLiveData()

    var pokemonInfoPageAdapter: WeakReference<PokemonInfoPageAdapter?> = WeakReference(null)

    var pokemonTypeAdapter: WeakReference<PokemonTypeAdapter?> = WeakReference(PokemonTypeAdapter())

    var pokemonAbilityAdapter: WeakReference<PokemonAbilityAdapter?> =
        WeakReference(PokemonAbilityAdapter())

    var pokemonStatAdapter: WeakReference<PokemonStatsAdapter?> =
        WeakReference(PokemonStatsAdapter())

    var pokemonSpriteAdapter: WeakReference<PokemonSpriteAdapter?> = WeakReference(
        PokemonSpriteAdapter()
    )

    var pokemonOtherFormsAdapter: WeakReference<PokemonSpriteAdapter?> = WeakReference(
        PokemonSpriteAdapter()
    )

    var moveByPokemonAdapter: WeakReference<PokemonMoveListAdapter?> = WeakReference(
        PokemonMoveListAdapter()
    )

    var evolutionPokemonAdapter: WeakReference<PokemonEvolutionChainAdapter?> = WeakReference(
        PokemonEvolutionChainAdapter()
    )

    var statsTotal = ObservableField<Int?>()

    var pokemonDescription = ObservableField<String?>()

    fun postPokemonInfo(pokemonInfo: PokemonInfo) {
        _pokemonInfo.postValue(pokemonInfo)
    }

    fun postPokemonStats(stats: List<Stat>) {
        _pokemonStats.postValue(stats)
    }

    fun postPokemonMoves(moves: List<MoveList>) {
        _pokemonMoves.postValue(moves.sortedBy {
            it.order
        })
    }

    private fun postPokemonEvolutionChain(evolutionChain: EvolutionChain) {
        _pokemonEvolutionChain.postValue(evolutionChain)
    }

    private fun postPokemonEvolutionChainList(evolutionChainList: List<ChainLink>) {
        _pokemonEvolutionList.postValue(evolutionChainList)
    }

    fun postPokemonSprites(pokemonSprites: List<Pair<String, String>>) {
        _pokemonSpritesUrl.postValue(pokemonSprites)
    }

    fun postPokemonOtherForms(pokemonOtherForms: List<Pair<String, String>>) {
        _pokemonOtherFormsUrl.postValue(pokemonOtherForms)
    }

    fun postAbilityInfo(abilityInfo: AbilityInfo) {
        _abilityInfo.postValue(abilityInfo)
    }

    fun postMoveInfo(moveInfo: MoveInfo) {
        _moveInfo.postValue(moveInfo)
    }


    fun loadPokemonInfo(pokemonList: NamedResourceApi) {
        viewModelScope.launch (Dispatchers.IO) {
            loading.postValue(true)
            var pokemonInfo: PokemonInfo? = null

            pokemonInfo = if (urlProvider.extractIdFromUrl(pokemonList.url) != null) {
                pokemonRemoteRepository.getPokemonInfo(urlProvider.extractIdFromUrl(pokemonList.url))
            } else {
                pokemonRemoteRepository.getPokemonInfo(pokemonList.name)
            }

            var specie = pokemonSpecieRemoteRepository.getSpecieInfo(pokemonInfo.id)

            if (specie != null && !specie.name.isNullOrEmpty()) {
                if (!specie.evolutionChain?.url.isNullOrEmpty()) {
                    var evolChain = pokemonEvolutionChainRemoteRepository.getEvolutionChain(
                        urlProvider.extractIdFromUrl(specie.evolutionChain.let {
                            if (it != null && !it.url.isNullOrEmpty())
                                it.url
                            else
                                "0"
                        })
                    )
                    postPokemonEvolutionChain(evolChain)
                    var evoList = mutableListOf(evolChain.chain!!)
                    postPokemonEvolutionChainList(handleEvolutionChain(evoList, evolChain.chain!!))
                }
                pokemonInfo.specie = specie
                if (specie.flavorText.isNotEmpty()) {
                    var find = false
                    var pos = 0
                    while (!find && pos < specie.flavorText.size) {
                        if (pokemonInfo.specie.flavorText[pos].language == "en") {
                            pokemonDescription.set(pokemonInfo.specie.flavorText[pos].description)
                            find = true
                        } else
                            pos++
                    }
                }
            }else{
                pokemonInfo.specie = SpecieInfo()
                postPokemonEvolutionChain(EvolutionChain())
                postPokemonEvolutionChainList(listOf())
            }

            postPokemonInfo(pokemonInfo)
            postPokemonMoves(pokemonInfo.moves)
            postPokemonStats(pokemonInfo.stats)

            var pokemonSprite = mutableListOf<Pair<String, String>>()
            pokemonInfo.sprites.frontHome.let {
                if (!it.isNullOrEmpty()) {
                    pokemonSprite.add(Pair(it, context.getString(R.string.home)))
                }
            }
            pokemonInfo.sprites.frontHomeShiny.let {
                if (!it.isNullOrEmpty()) {
                    pokemonSprite.add(Pair(it, context.getString(R.string.home_shiny)))
                }
            }
            pokemonInfo.sprites.frontDefault.let {
                if (!it.isNullOrEmpty()) {
                    pokemonSprite.add(Pair(it, context.getString(R.string.front)))
                }
            }
            pokemonInfo.sprites.frontFemale.let {
                if (!it.isNullOrEmpty()) {
                    pokemonSprite.add(Pair(it, context.getString(R.string.female)))
                }
            }
            pokemonInfo.sprites.frontShiny.let {
                if (!it.isNullOrEmpty()) {
                    pokemonSprite.add(Pair(it, context.getString(R.string.front_shiny)))
                }
            }
            pokemonInfo.sprites.frontShinyFemale.let {
                if (!it.isNullOrEmpty()) {
                    pokemonSprite.add(Pair(it, context.getString(R.string.female_shiny)))
                }
            }
            postPokemonSprites(pokemonSprite)

            var pokemonOtherForms = mutableListOf<Pair<String, String>>()
            specie.varieties.forEach {
                if (!it.pokemon.name.isNullOrEmpty() && pokemonInfo.name != it.pokemon.name) {
                    pokemonOtherForms.add(
                        Pair(
                            it.pokemon.url,
                            it.pokemon.name.replace("-".toRegex(), " ").uppercase()
                        )
                    )
                }
            }
            postPokemonOtherForms(pokemonOtherForms)

            statsTotal.set(pokemonInfo.totalStat())
            loading.postValue(false)
        }
    }

    private fun handleEvolutionChain(
        evoList: MutableList<ChainLink>,
        chain: ChainLink
    ): MutableList<ChainLink> {
        return if (chain?.evolvesTo!!.isNotEmpty()) {
            var i = ChainLink()
            for (item in chain?.evolvesTo!!) {
                i = item
                evoList.add(item)
            }
            handleEvolutionChain(evoList, i)
        } else {
            evoList
        }
    }

    fun loadAbilityInfo(ability: NamedResourceApi) {
        viewModelScope.launch(Dispatchers.IO) {
            loading.postValue(true)
            var abilityInfo: AbilityInfo? = null

            abilityInfo = if (urlProvider.extractIdFromUrl(ability.url) != null) {
                abilityRemoteRepository.getAbility(urlProvider.extractIdFromUrl(ability.url))
            } else {
                abilityRemoteRepository.getAbility(ability.name)
            }
            postAbilityInfo(abilityInfo)
            loading.postValue(false)
        }
    }

    fun loadMoveInfo(move: NamedResourceApi) {
        viewModelScope.launch(Dispatchers.IO) {
            loading.postValue(true)
            var moveInfo: MoveInfo? = null

            moveInfo = if (urlProvider.extractIdFromUrl(move.url) != null) {
                moveRemoteRepository.getMove(urlProvider.extractIdFromUrl(move.url))
            } else {
                moveRemoteRepository.getMove(move.name)
            }
            postMoveInfo(moveInfo)
            loading.postValue(false)
        }
    }
}