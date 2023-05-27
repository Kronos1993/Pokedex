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
import com.kronos.pokedex.domian.model.move.MoveDetail
import com.kronos.pokedex.domian.model.move.MoveInfo
import com.kronos.pokedex.domian.model.move.MoveList
import com.kronos.pokedex.domian.model.pokemon.PokemonInfo
import com.kronos.pokedex.domian.model.pokemon.extension.totalStat
import com.kronos.pokedex.domian.model.specie.SpecieInfo
import com.kronos.pokedex.domian.model.stat.Stat
import com.kronos.pokedex.domian.repository.*
import com.kronos.pokedex.ui.abilities.PokemonAbilityAdapter
import com.kronos.pokedex.ui.move.PokemonMoveListAdapter
import com.kronos.pokedex.ui.move.ShowMoveIn
import com.kronos.pokedex.ui.pokemon.detail.adapter.*
import com.kronos.pokedex.ui.pokemon.detail.domain.GenderPossibility
import com.kronos.pokedex.ui.pokemon.detail.domain.getPossibilities
import com.kronos.pokedex.ui.stats.PokemonStatsAdapter
import com.kronos.pokedex.util.preferences.PreferencesUtil
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

    private val _pokemonInfo = MutableLiveData<PokemonInfo?>()
    val pokemonInfo = _pokemonInfo.asLiveData()

    private val _specieInfo = MutableLiveData<SpecieInfo?>()
    val specieInfo = _specieInfo.asLiveData()

    private val _pokemonStats = MutableLiveData<List<Stat>>()
    val pokemonStats = _pokemonStats.asLiveData()

    private val _pokemonMovesToShow = MutableLiveData<List<MoveList>>()
    val pokemonMovesToShow = _pokemonMovesToShow.asLiveData()

    private val _allMoves = MutableLiveData<List<MoveList>>()
    val allMoves = _allMoves.asLiveData()

    private val _movesLevelUp = MutableLiveData<List<MoveList>>()
    val movesLevelUp = _movesLevelUp.asLiveData()

    private val _movesTutor = MutableLiveData<List<MoveList>>()
    val movesTutor = _movesTutor.asLiveData()

    private val _movesTM = MutableLiveData<List<MoveList>>()
    val movesTM = _movesTM.asLiveData()

    private val _movesEgg = MutableLiveData<List<MoveList>>()
    val movesEgg = _movesEgg.asLiveData()

    private val _movesOther = MutableLiveData<List<MoveList>>()
    val movesOther = _movesOther.asLiveData()

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

    var pokemonEggGroupAdapter: WeakReference<PokemonEggGroupAdapter?> = WeakReference(PokemonEggGroupAdapter())

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
        PokemonMoveListAdapter(ShowMoveIn.POKEMON_DETAIL)
    )

    var evolutionPokemonAdapter: WeakReference<PokemonEvolutionChainAdapter?> = WeakReference(
        PokemonEvolutionChainAdapter()
    )

    var statsTotal = ObservableField<Int?>()

    var pokemonDescription = ObservableField<String?>()

    var pokemonName = ObservableField<String?>()

    var pokemonGenera = ObservableField<String?>()
    var pokemonGenderPossibility = ObservableField<GenderPossibility?>()

    var showMove = ObservableField<String?>()
    var buttonSelected = ObservableField<String?>()

    fun postPokemonInfo(pokemonInfo: PokemonInfo?) {
        _pokemonInfo.postValue(pokemonInfo)
    }

    fun postSpecieInfo(specie: SpecieInfo?) {
        _specieInfo.postValue(specie)
    }

    fun postPokemonStats(stats: List<Stat>) {
        _pokemonStats.postValue(stats)
    }

    fun postPokemonMoves(moves: List<MoveList>) {
        _pokemonMovesToShow.postValue(moves.sortedBy {
            it.order
        })
    }

    private fun groupMoves(moves: List<MoveList>){
        var levelUp = mutableListOf<MoveList>()
        var tutor = mutableListOf<MoveList>()
        var tm = mutableListOf<MoveList>()
        var egg = mutableListOf<MoveList>()
        var other = mutableListOf<MoveList>()

        var moveDetail: MoveDetail? = null
        for (move in moves){
            if (move.moveDetails.isNotEmpty()){
                moveDetail = move.moveDetails[0]
                when (moveDetail.moveLearnMethod) {
                    "level-up" -> {
                        levelUp.add(move)
                    }
                    "egg" -> {
                        egg.add(move)
                    }
                    "machine" -> {
                        tm.add(move)
                    }
                    "tutor" -> {
                        tutor.add(move)
                    }
                    else -> {
                        other.add(move)
                    }
                }
            }
        }
        _movesLevelUp.postValue(levelUp)
        _movesTM.postValue(tm)
        _movesTutor.postValue(tutor)
        _movesEgg.postValue(egg)
        _movesOther.postValue(other)
        _allMoves.postValue(moves)
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
        pokemonDescription.set(null)
        pokemonName.set(null)
        pokemonGenera.set(null)
        pokemonGenderPossibility.set(GenderPossibility())
        viewModelScope.launch (Dispatchers.IO) {
            loading.postValue(true)
            var pokemonInfo: PokemonInfo? = null
            statsTotal.set(0)

            pokemonInfo = if (urlProvider.extractIdFromUrl(pokemonList.url) != null) {
                pokemonRemoteRepository.getPokemonInfo(urlProvider.extractIdFromUrl(pokemonList.url))
            } else {
                pokemonRemoteRepository.getPokemonInfo(pokemonList.name)
            }

            var specie = pokemonSpecieRemoteRepository.getSpecieInfo(pokemonInfo.id)

            if (specie != null && !specie.name.isNullOrEmpty()) {
                var genderPossibility = GenderPossibility()
                genderPossibility.getPossibilities(specie.genderRate)
                pokemonGenderPossibility.set(genderPossibility)
                pokemonInfo.specie = specie
                postSpecieInfo(specie)

            }else{
                pokemonName.set(pokemonInfo.name)
                pokemonInfo.specie = SpecieInfo()
                postPokemonEvolutionChain(EvolutionChain())
                postPokemonEvolutionChainList(listOf())
            }

            postPokemonInfo(pokemonInfo)
            postPokemonMoves(pokemonInfo.moves)
            groupMoves(pokemonInfo.moves)
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

    fun getPokemonName(specie :SpecieInfo?){
        if (specie!=null){
            if (specie.names.isNotEmpty()) {
                var find = false
                var pos = 0
                while (!find && pos < specie.names.size) {
                    if (specie.names[pos].language.name == PreferencesUtil.getLanguagePreference(context)) {
                        pokemonName.set(specie.names[pos].name)
                        find = true
                    } else
                        pos++
                }
            }else{
                pokemonName.set(pokemonInfo.value?.name)
            }
        }
    }

    fun getPokemonDescription(specie:SpecieInfo?){
       if (specie!=null){
           if (specie.flavorText.isNotEmpty()) {
               var find = false
               var pos = 0
               while (!find && pos < specie.flavorText.size) {
                   if (specie.flavorText[pos].language == PreferencesUtil.getLanguagePreference(context)) {
                       pokemonDescription.set(specie.flavorText[pos].description)
                       find = true
                   } else
                       pos++
               }
           }
       }
    }

    fun getPokemonGenera(specie:SpecieInfo?){
        if (specie!=null){
            if (specie.genera.isNotEmpty()) {
                var find = false
                var pos = 0
                while (!find && pos < specie.genera.size) {
                    if (specie.genera[pos].language == PreferencesUtil.getLanguagePreference(context)) {
                        pokemonGenera.set(specie.genera[pos].genus)
                        find = true
                    } else
                        pos++
                }
            }
        }
    }

    fun getPokemonEvolution(){
        viewModelScope.launch (Dispatchers.IO){
            if (!pokemonInfo.value?.specie?.evolutionChain?.url.isNullOrEmpty()) {
                var evolChain = pokemonEvolutionChainRemoteRepository.getEvolutionChain(
                    urlProvider.extractIdFromUrl(pokemonInfo.value?.specie?.evolutionChain.let {
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
        }
    }

    private fun handleEvolutionChain(
        evoList: MutableList<ChainLink>,
        chain: ChainLink
    ): MutableList<ChainLink> {
        if (evoList.size==1){
            evoList[0].run {
                if(pokemonInfo.value?.name.equals(this.species.name))
                    this.isCurrentSelected = true
                this
            }
        }
        return if (chain?.evolvesTo!!.isNotEmpty()) {
            var i = ChainLink()
            for (item in chain?.evolvesTo!!) {
                i = item
                i.evolvesFrom = chain.species.name
                if(pokemonInfo.value?.name.equals(i.species.name))
                    i.isCurrentSelected = true
                evoList.add(i)
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

    fun setShowMove(move: String) {
        when(move){
            "all"->{
                _allMoves.value?.let { postPokemonMoves(it) }
            }
            "egg"->{
                _movesEgg.value?.let { postPokemonMoves(it) }
            }
            "tutor"->{
                _movesTutor.value?.let { postPokemonMoves(it) }
            }
            "tm"->{
                _movesTM.value?.let { postPokemonMoves(it) }
            }
            "level-up"->{
                _movesLevelUp.value?.let { postPokemonMoves(it) }
            }
            "other"->{
                _movesOther.value?.let { postPokemonMoves(it) }
            }
        }
    }

}