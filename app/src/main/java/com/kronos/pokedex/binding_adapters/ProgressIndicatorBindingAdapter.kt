package com.kronos.pokedex.binding_adapters

import androidx.databinding.BindingAdapter
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.kronos.pokedex.R


@BindingAdapter("app:set_progress_color")
fun setProgressColor(view: LinearProgressIndicator, stat: String) {
    view.run {
        when {
            stat.equals("hp",false) -> {
                view.setIndicatorColor(view.context.getColor(R.color.hp_color))
            }
            stat.equals("attack",false) -> {
                view.setIndicatorColor(view.context.getColor(R.color.attack_color))
            }
            stat.equals("defense",false) -> {
                view.setIndicatorColor(view.context.getColor(R.color.defense_color))
            }
            stat.equals("special-attack",false) -> {
                view.setIndicatorColor(view.context.getColor(R.color.sattack_color))
            }
            stat.equals("special-defense",false) -> {
                view.setIndicatorColor(view.context.getColor(R.color.sdefense_color))
            }
            stat.equals("speed",false) -> {
                view.setIndicatorColor(view.context.getColor(R.color.speed_color))
            }
        }
    }
}
