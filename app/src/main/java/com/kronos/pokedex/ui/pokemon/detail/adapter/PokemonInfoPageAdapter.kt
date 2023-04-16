package com.kronos.pokedex.ui.pokemon.detail.adapter

import android.graphics.drawable.Drawable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class PokemonInfoPageAdapter(
    var fragment: FragmentManager,
    var lifecycle: Lifecycle,
    var fragments: MutableList<Triple<String,Fragment,Drawable?>>,
) : FragmentStateAdapter(fragment,lifecycle) {

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int) = fragments[position].second

    fun getPageTitle(position: Int): CharSequence? {
        return fragments[position].first
    }

    fun getPageIcon(position: Int): Drawable? {
        return fragments[position].third
    }

    fun getFragment(position: Int): Fragment? {
        return fragments[position].second
    }
}