package com.kronos.pokedex.binding_adapters

import android.os.Build
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.card.MaterialCardView
import com.kronos.pokedex.R
import com.kronos.pokedex.domian.model.item.BerryFlavor
import com.kronos.pokedex.domian.model.move.MoveInfo
import com.kronos.pokedex.domian.model.specie.SpecieInfo
import com.kronos.pokedex.domian.model.specie.getCaptureRate
import com.kronos.pokedex.domian.model.specie.getHatchCounter

@BindingAdapter("app:set_stat_name")
fun setStatName(view: TextView, stat: String) {
    view.run {
        when {
            stat.equals("hp", false) -> {
                view.text = view.context.getString(R.string.hp)
            }
            stat.equals("attack", false) -> {
                view.text = view.context.getString(R.string.attack)
            }
            stat.equals("defense", false) -> {
                view.text = view.context.getString(R.string.defense)
            }
            stat.equals("special-attack", false) -> {
                view.text = view.context.getString(R.string.special_attack)
            }
            stat.equals("special-defense", false) -> {
                view.text = view.context.getString(R.string.special_defense)
            }
            stat.equals("speed", false) -> {
                view.text = view.context.getString(R.string.speed)
            }
        }
    }
}

@BindingAdapter("app:pokemon_type")
fun setPokemonType(view: TextView, specie: SpecieInfo?) {
    view.run {
        if (specie != null) {
            visibility = View.VISIBLE
            when {
                specie.isBaby -> {
                    view.text = view.context.getString(R.string.baby_pokemon)
                }
                specie.isLegendary -> {
                    view.text = view.context.getString(R.string.legendary_pokemon)
                }
                specie.isMythical -> {
                    view.text = view.context.getString(R.string.mythical_pokemon)
                }
                else -> {
                    visibility = View.GONE
                }
            }
        }
    }
}

@BindingAdapter("app:pokemon_genera")
fun setPokemonGenera(view: TextView, genera: String?) {
    view.run {
        view.text = genera?.replace("-"," ") ?: view.context.getString(R.string.unknown)
    }
}

@BindingAdapter("app:handle_text")
fun transformText(view: TextView, text: String?) {
    view.run {
        if (!text.isNullOrEmpty()) {
            var textMod = text.replace(Regex("-"), " ")
            textMod = replaceNewLine(textMod)
            if (textMod.length > 1) {
                view.text = textMod.substring(0, 1).uppercase() + textMod.substring(1).lowercase()
            } else {
                view.text = textMod
            }
        } else {
            view.text = view.context.getString(R.string.unknown)
        }
    }
}

@BindingAdapter("app:handle_egg_group_name")
fun transformEggGroupName(view: TextView, text: String?) {
    view.run {
        if (text != null) {
            val regex = Regex("[0-9]") // Matches any digit from 0 to 9
            view.text = text.replace(regex, "")
        } else {
            view.text = view.context.getString(R.string.unknown)
        }
    }
}

@BindingAdapter("app:handle_long_item_effect_text")
fun transformItemLongEffectText(view: TextView, text: String?) {
    view.run {
        view.text = text?.replace(Regex("\n"), " ") ?: view.context.getString(R.string.unknown)
    }
}

@BindingAdapter("app:handle_pokemon_description")
fun handlePokemonDescription(view: TextView, text: String?) {
    view.run {
        view.text = text?.replace(Regex("\n"), " ") ?: view.context.getString(R.string.unknown)

    }
}

@BindingAdapter("app:handle_move_description")
fun handleMoveDescription(view: TextView, text: String?) {
    view.run {
        view.text = text?.replace(Regex("\n"), " ") ?: view.context.getString(R.string.unknown)
    }
}

@BindingAdapter("app:handle_move_effect")
fun handleMoveEffect(view: TextView, moveInfo:MoveInfo?) {
    view.run {
        if (moveInfo !=null)
            view.text = text?.replace(Regex("effect_chance"), moveInfo.effectChance.toString())?.replace("$","")
        else{
            view.context.getString(R.string.unknown)
        }
    }
}

@BindingAdapter("app:handle_pokemon_name")
fun setPokemonName(view: TextView, pokemonName: String?) {
    view.run {
        view.text = if (pokemonName != null) {
            var name = pokemonName.replace(Regex("-"), " ")
            name.uppercase()
        } else
            view.context.getString(R.string.unknown)
    }
}

@BindingAdapter("app:handle_pokedex_name")
fun setPokedexName(view: TextView, pokedexName: String?) {
    view.run {
        if (pokedexName != null) {
            var name = pokedexName.replace(Regex("updated-"), "").replace(Regex("extended-"), "").replace(Regex("letsgo-"), "")
                .replace(Regex("-"), " ")
            view.text = if (name.length > 1) {
                name.uppercase()
            } else {
                view.context.getString(R.string.unknown)
            }
        } else
            view.text = view.context.getString(R.string.unknown)
    }
}

@BindingAdapter("app:handle_pokemon_height")
fun setPokemonHeight(view: TextView, height: Double?) {
    view.run {
        view.text = if (height != null) {
            "${(height / 10)} mts"
        } else
            view.context.getString(R.string.unknown)
    }
}

@BindingAdapter("app:handle_pokemon_weight")
fun setPokemonWeight(view: TextView, weight: Double?) {
    view.run {
        view.text = if (weight != null) {
            "${(weight / 10)} kg"
        } else
            view.context.getString(R.string.unknown)
    }
}

@BindingAdapter("app:handle_move_category")
fun setMoveCategory(view: TextView, category: String?) {
    view.run {
        if (category != null) {
            when (category) {
                "physical" -> {
                    view.text = view.context.getString(R.string.move_physical)
                }
                "special" -> {
                    view.text = view.context.getString(R.string.move_special)
                }
                else -> {
                    view.text = view.context.getString(R.string.move_status)
                }
            }
        }
    }
}

@BindingAdapter("app:handle_increased_stat")
fun setIncreasedStat(view: TextView, increasedStat: String?) {
    view.run {
        view.text = increasedStat?.replace(Regex("-"), " ")?.uppercase()
            ?: view.context.getString(R.string.none).uppercase()
    }
}

@BindingAdapter("app:handle_decreased_stat")
fun setDecreasedStat(view: TextView, decreasedStat: String?) {
    view.run {
        view.text = decreasedStat?.replace(Regex("-"), " ")?.uppercase()
            ?: view.context.getString(R.string.none).uppercase()
    }
}

@BindingAdapter("app:handle_hates_flavor")
fun setHatesFlavor(view: TextView, hatesFlavor: String?) {
    view.run {
        view.text = hatesFlavor?.uppercase() ?: view.context.getString(R.string.none).uppercase()
    }
}

@BindingAdapter("app:handle_likes_flavor")
fun setLikesFlavor(view: TextView, likesFlavor: String?) {
    view.run {
        view.text = likesFlavor?.uppercase() ?: view.context.getString(R.string.none).uppercase()
    }
}

@BindingAdapter("app:handle_base_happiness")
fun setBaseHappiness(view: TextView, baseHappiness: Int?) {
    view.run {
        if (baseHappiness != null)
            text = when {
                0 <= baseHappiness && baseHappiness < 50 -> {
                    view.context.getString(R.string.lower_happiness)
                }
                50 <= baseHappiness && baseHappiness < 100 -> {
                    view.context.getString(R.string.normal_happiness)
                }
                baseHappiness >= 100 -> {
                    view.context.getString(R.string.high_happiness)
                }
                else -> {
                    view.context.getString(R.string.unknown)
                }
            }
        else text = view.context.getString(R.string.unknown)
    }
}

@BindingAdapter("app:handle_capture_rate")
fun setCaptureRate(view: TextView, specie: SpecieInfo?) {
    view.run {
        text = if(specie !=null){
            view.context.getString(R.string.capture_rate_value).format(specie.captureRate,specie.getCaptureRate())
        }
        else
            view.context.getString(R.string.unknown)
    }
}

@BindingAdapter("app:handle_hatch_counter")
fun setHatchCounter(view: TextView, specie: SpecieInfo?) {
    view.run {
        text = if(specie !=null)
            String.format(view.context.getString(R.string.hatch_counter_value),specie.hatchCounter,specie.getHatchCounter())
        else
            view.context.getString(R.string.unknown)
    }
}

@BindingAdapter("app:is_current_pokemon")
fun isCurrentPokemon(view: TextView, is_current_pokemon: Boolean) {
    view.run {
        if(is_current_pokemon)
            view.setTextColor(ContextCompat.getColor(view.context,R.color.selected_pokemon))
        else
            view.setTextColor(ContextCompat.getColor(view.context, com.kronos.resources.R.color.blackText))
    }
}

fun replaceNewLine(text :String) = text.replace(Regex("\n"), " ")

@BindingAdapter("handle_move_full_description")
fun handleMoveFullDescription(view: TextView, description: String?) = view.run {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        if (description != null) {
            view.tooltipText = description
        }
    }
}