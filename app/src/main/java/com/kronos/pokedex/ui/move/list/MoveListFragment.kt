package com.kronos.pokedex.ui.move.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kronos.core.extensions.binding.fragmentBinding
import com.kronos.pokedex.R
import com.kronos.pokedex.databinding.FragmentMoveListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoveListFragment : Fragment() {

    private val binding by fragmentBinding<FragmentMoveListBinding>(R.layout.fragment_move_list)

    private val viewModel by viewModels<MoveListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.run {
        viewModel = this@MoveListFragment.viewModel
        lifecycleOwner = this@MoveListFragment.viewLifecycleOwner
        root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.unbind()
    }
}