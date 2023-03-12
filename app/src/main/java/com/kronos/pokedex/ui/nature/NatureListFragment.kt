package com.kronos.pokedex.ui.nature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kronos.core.extensions.binding.fragmentBinding
import com.kronos.pokedex.R
import com.kronos.pokedex.databinding.FragmentNatureListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NatureListFragment : Fragment() {

    private val binding by fragmentBinding<FragmentNatureListBinding>(R.layout.fragment_nature_list)

    private val viewModel by viewModels<NatureListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.run {
        viewModel = this@NatureListFragment.viewModel
        lifecycleOwner = this@NatureListFragment.viewLifecycleOwner
        root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.unbind()
    }
}