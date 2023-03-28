package com.kronos.pokedex.binding_adapters

import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.google.android.material.card.MaterialCardView
import com.kronos.pokedex.R


@BindingAdapter("handle_visibility")
fun handleVisibility(view: View, enable: Boolean) = view.run {
    isVisible = enable
}

@BindingAdapter("handle_visibility")
fun handleVisibility(view: View, text: String?) = view.run {
    isVisible = !text.isNullOrEmpty()
}

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
                view.setCardBackgroundColor(ContextCompat.getColor(view.context,R.color.move_physical_color))
            }
            "special" -> {
                view.setCardBackgroundColor(ContextCompat.getColor(view.context,R.color.move_special_color))
            }
            else -> {
                view.setCardBackgroundColor(ContextCompat.getColor(view.context,R.color.move_status_color))
            }
        }
    }
}