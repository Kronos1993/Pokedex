package com.kronos.pokedex.binding_adapters

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout


@BindingAdapter("error_text")
fun setErrorText(view: TextInputLayout, errorMessageRes: Int) {
    view.run {
        if (errorMessageRes == 0) {
            view.error = null
            view.isErrorEnabled = false
            return@run
        }

        val errorMessage = context.getString(errorMessageRes)
        if (errorMessage.isEmpty()) {
            view.error = null
            view.isErrorEnabled = false
        } else {
            view.error = errorMessage
            view.isErrorEnabled = true

            if (view.endIconDrawable != null) {
                view.errorIconDrawable = null
            }
        }
    }
}

@BindingAdapter("error_text")
fun setErrorText(input: TextInputLayout, value: String?) =
    input.run {
        this.isErrorEnabled = value.orEmpty().isNotEmpty()
        this.error = value

        if (this.endIconDrawable != null) {
            this.errorIconDrawable = null
        }
    }

@BindingAdapter("error_text")
fun setErrorText(view: TextView, errorMessageRes: Int) {
    view.run {
        if (errorMessageRes == 0) {
            view.text = null
            view.visibility = View.GONE
            return@run
        }

        val errorMessage = context.getString(errorMessageRes)
        if (errorMessage.isEmpty()) {
            view.text = null
            view.visibility = View.GONE
        } else {
            view.text = errorMessage
            view.visibility = View.VISIBLE
        }
    }
}

@BindingAdapter("error_text")
fun setErrorText(input: TextView, value: String?) =
    input.run {
        this.text = value
        this.visibility = View.VISIBLE
    }
