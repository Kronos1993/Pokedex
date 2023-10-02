package com.kronos.pokedex.binding_adapters

import android.os.Build
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.card.MaterialCardView
import com.kronos.pokedex.R
import com.kronos.pokedex.domian.model.item.BerryFlavor

@BindingAdapter("is_hidden_ability")
fun isHiddenAbility(view: MaterialCardView, hidden: Boolean) = view.run {
    if (hidden)
        view.setCardBackgroundColor(ContextCompat.getColor(view.context, R.color.hidden_ability))
    else
        view.setCardBackgroundColor(
            ContextCompat.getColor(
                view.context,
                com.kronos.resources.R.color.backgroundCardColor
            )
        )
}

@BindingAdapter("move_category_color")
fun setMoveCategoryColor(view: MaterialCardView, category: String?) = view.run {
    if (category != null) {
        when (category) {
            "physical" -> {
                view.setCardBackgroundColor(ContextCompat.getColor(view.context, R.color.move_physical_color))
            }
            "special" -> {
                view.setCardBackgroundColor(ContextCompat.getColor(view.context, R.color.move_special_color))
            }
            else -> {
                view.setCardBackgroundColor(ContextCompat.getColor(view.context, R.color.move_status_color))
            }
        }
    }
}

@BindingAdapter("handle_berry_potency")
fun handleBerryPotency(view: MaterialCardView, potency: Int?) = view.run {
    if (potency != null) {
        when {
            potency in 1..5 -> {
                view.setCardBackgroundColor(ContextCompat.getColor(view.context, R.color.berry_potency_min))
            }
            potency in 5..10 -> {
                view.setCardBackgroundColor(ContextCompat.getColor(view.context, R.color.berry_potency_med))
            }
            potency > 10 -> {
                view.setCardBackgroundColor(ContextCompat.getColor(view.context, R.color.berry_potency_high))
            }
        }
    }
}

@BindingAdapter("handle_berry_potency_description")
fun handleBerryPotencyDescription(view: MaterialCardView, flavor: BerryFlavor?) = view.run {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        if (flavor != null) {
            when {
                flavor.potency == 0 -> {
                    view.tooltipText = String.format(view.context.getString(R.string.berry_potency_none),flavor.flavor.name)
                }
                flavor.potency in 1..5 -> {
                    view.tooltipText = String.format(view.context.getString(R.string.berry_potency_low),flavor.flavor.name)
                }
                flavor.potency in 5..10 -> {
                    view.tooltipText = String.format(view.context.getString(R.string.berry_potency_medium),flavor.flavor.name)
                }
                flavor.potency > 10 -> {
                    view.tooltipText = String.format(view.context.getString(R.string.berry_potency_high),flavor.flavor.name)
                }
            }
        }
    }
}