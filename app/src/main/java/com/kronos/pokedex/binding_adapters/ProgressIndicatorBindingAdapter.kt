package com.kronos.pokedex.binding_adapters

import android.view.animation.Animation
import android.view.animation.Transformation
import androidx.databinding.BindingAdapter
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.kronos.pokedex.R
import kotlin.math.abs


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

@BindingAdapter("app:animate_progress")
fun animateProgress(view: LinearProgressIndicator, stat: Int) {
    view.run {
        val mProgressAnimation = ProgressBarAnimation(view, 10000)
        mProgressAnimation.setProgress(progress);
    }
}

class ProgressBarAnimation(progressBar: LinearProgressIndicator, fullDuration: Long) :
    Animation() {
    private val mProgressBar: LinearProgressIndicator = progressBar
    private var mTo = 0
    private var mFrom = 0
    private val mStepDuration: Long = fullDuration / progressBar.max
    fun setProgress(progress: Int) {
        var progress = progress
        if (progress < 0) {
            progress = 0
        }
        if (progress > mProgressBar.max) {
            progress = mProgressBar.max
        }
        mTo = progress
        mFrom = mProgressBar.progress
        duration = abs(mTo - mFrom) * mStepDuration
        mProgressBar.startAnimation(this)
    }

    override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
        val value = mFrom + (mTo - mFrom) * interpolatedTime
        mProgressBar.progress = value.toInt()
    }

}
