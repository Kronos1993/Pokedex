package com.kronos.pokedex.ui.pokemon.detail.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class PokemonInfoPageAdapter(
    var fragment: FragmentActivity,
    var fragments: MutableList<Fragment>,
    var titles: MutableList<String>
) : FragmentStateAdapter(fragment) {

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int) = fragments[position]

    fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }
    fun getFragment(position: Int): Fragment? {
        return fragments[position]
    }
}