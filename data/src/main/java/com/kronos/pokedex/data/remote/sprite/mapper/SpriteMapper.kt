package com.kronos.pokedex.data.remote.sprite.mapper

import com.kronos.pokedex.data.remote.sprite.dto.SpriteDto
import com.kronos.pokedex.domian.model.sprite.Sprite

fun SpriteDto.toSprite(): Sprite =
    Sprite(
        default.let {
            if(it.isNullOrEmpty())
                ""
            else
                it
        },
        backDefault.let {
            if(it.isNullOrEmpty())
                ""
            else
                it
        },
        backFemale.let {
            if(it.isNullOrEmpty())
                ""
            else
                it
        },
        backShiny.let {
            if(it.isNullOrEmpty())
                ""
            else
                it
        },
        backShinyFemale.let {
            if(it.isNullOrEmpty())
                ""
            else
                it
        },
        frontDefault.let {
            if(it.isNullOrEmpty())
                ""
            else
                it
        },
        frontFemale.let {
            if(it.isNullOrEmpty())
                ""
            else
                it
        },
        frontShiny.let {
            if(it.isNullOrEmpty())
                ""
            else
                it
        },
        frontShinyFemale.let {
            if(it.isNullOrEmpty())
                ""
            else
                it
        },
        otherSprites.home.frontHome.let {
            if(it.isNullOrEmpty())
                ""
            else
                it
        },
        otherSprites.home.frontHomeShiny.let {
            if(it.isNullOrEmpty())
                ""
            else
                it
        },
    )
