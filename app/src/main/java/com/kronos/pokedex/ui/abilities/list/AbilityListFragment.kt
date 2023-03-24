package com.kronos.pokedex.ui.abilities.list

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kronos.pokedex.R

const val CURRENT_ABILITY = "current_ability"

class AbilityListFragment : Fragment() {

    companion object {
        fun newInstance() = AbilityListFragment()
    }

    private lateinit var viewModel: AbilityListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ability_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AbilityListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}