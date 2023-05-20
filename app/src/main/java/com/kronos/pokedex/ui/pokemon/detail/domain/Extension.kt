package com.kronos.pokedex.ui.pokemon.detail.domain

import com.kronos.pokedex.R

fun GenderPossibility.getPossibilities(genderRate:Int){
    if(genderRate !=null){
        when(genderRate){
            -1 -> {
                this.male = -1.0
                this.female = -1.0
                this.genderless = true
            }
            0 -> {
                this.male = 100.0
                this.female = 0.0
                this.genderless = false
            }
            1 -> {
                this.male = 87.5
                this.female = 12.5
                this.genderless = false
            }
            4 -> {
                this.male = 50.0
                this.female = 50.0
                this.genderless = false
            }
            6 -> {
                this.male = 25.0
                this.female = 75.0
                this.genderless = false
            }
            8 -> {
                this.male = 0.0
                this.female = 100.0
                this.genderless = false
            }

        }
    }
}