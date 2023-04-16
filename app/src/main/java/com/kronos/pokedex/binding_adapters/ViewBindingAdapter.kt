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

