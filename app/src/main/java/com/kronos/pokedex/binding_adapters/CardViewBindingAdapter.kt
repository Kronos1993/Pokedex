package com.kronos.pokedex.binding_adapters

import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.card.MaterialCardView
import com.kronos.pokedex.R

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
                view.setCardBackgroundColor(ContextCompat.getColor(view.context, R.color.berry_potency_hight))
            }
        }
    }
}