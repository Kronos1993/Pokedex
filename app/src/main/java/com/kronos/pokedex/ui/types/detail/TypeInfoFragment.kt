package com.kronos.pokedex.ui.types.detail

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.kronos.core.extensions.binding.fragmentBinding
import com.kronos.core.util.LoadingDialog
import com.kronos.core.util.show
import com.kronos.pokedex.R
import com.kronos.pokedex.databinding.FragmentTypeInfoBinding
import com.kronos.pokedex.domian.model.NamedResourceApi
import com.kronos.pokedex.ui.types.list.CURRENT_TYPE
import dagger.hilt.android.AndroidEntryPoint
import java.lang.ref.WeakReference
import java.util.*

@AndroidEntryPoint
class TypeInfoFragment : Fragment() {
    private val binding by fragmentBinding<FragmentTypeInfoBinding>(R.layout.fragment_type_info)

    private val viewModel by activityViewModels<TypeInfoViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.run {
        viewModel = this@TypeInfoFragment.viewModel
        lifecycleOwner = this@TypeInfoFragment.viewLifecycleOwner
        setHasOptionsMenu(true)
        root
    }

    override fun onResume() {
        super.onResume()
        observeViewModel()
        initViews()
        setObservers()
        initViewModel()
    }

    private fun setObservers() {
        binding.buttonShowMove.setOnClickListener {}
        binding.buttonShowPokemon.setOnClickListener {}
    }

    private fun observeViewModel() {
        viewModel.doubleDamageFrom.observe(this.viewLifecycleOwner, ::handleDoubleDamageFrom)
        viewModel.halfDamageFrom.observe(this.viewLifecycleOwner, ::handleHalfDamageFrom)
        viewModel.noDamageFrom.observe(this.viewLifecycleOwner, ::handleNoDamageFrom)

        viewModel.doubleDamageTo.observe(this.viewLifecycleOwner, ::handleDoubleDamageTo)
        viewModel.halfDamageTo.observe(this.viewLifecycleOwner, ::handleHalfDamageTo)
        viewModel.noDamageTo.observe(this.viewLifecycleOwner, ::handleNoDamageTo)

        viewModel.loading.observe(this.viewLifecycleOwner, ::handleLoading)
        viewModel.error.observe(this.viewLifecycleOwner, ::handleError)
    }

    private fun handleDoubleDamageFrom(list: List<DamageRelationContainer>?) {
        viewModel.doubleDamageFromAdapter.get()?.submitList(list)
        viewModel.doubleDamageFromAdapter.get()?.notifyDataSetChanged()
    }

    private fun handleHalfDamageFrom(list: List<DamageRelationContainer>?) {
        viewModel.halfDamageFromAdapter.get()?.submitList(list)
        viewModel.halfDamageFromAdapter.get()?.notifyDataSetChanged()
    }

    private fun handleNoDamageFrom(list: List<DamageRelationContainer>?) {
        viewModel.noDamageFromAdapter.get()?.submitList(list)
        viewModel.noDamageFromAdapter.get()?.notifyDataSetChanged()
    }

    private fun handleDoubleDamageTo(list: List<DamageRelationContainer>?) {
        viewModel.doubleDamageToAdapter.get()?.submitList(list)
        viewModel.doubleDamageToAdapter.get()?.notifyDataSetChanged()
    }

    private fun handleHalfDamageTo(list: List<DamageRelationContainer>?) {
        viewModel.halfDamageToAdapter.get()?.submitList(list)
        viewModel.halfDamageToAdapter.get()?.notifyDataSetChanged()
    }

    private fun handleNoDamageTo(list: List<DamageRelationContainer>?) {
        viewModel.noDamageToAdapter.get()?.submitList(list)
        viewModel.noDamageToAdapter.get()?.notifyDataSetChanged()
    }

    private fun handleError(hashtable: Hashtable<String, String>) {
        if (hashtable["error"] != null) {
            if (hashtable["error"]!!.isNotEmpty()) {
                show(
                    binding.buttonShowPokemon,
                    hashtable["error"].orEmpty(),
                    com.kronos.resources.R.color.snack_bar_white,
                    com.kronos.resources.R.color.snack_bar_error_background
                )
            } else {
                show(
                    binding.buttonShowPokemon,
                    hashtable["error"].orEmpty(),
                    com.kronos.resources.R.color.snack_bar_white,
                    com.kronos.resources.R.color.snack_bar_success_background
                )
            }
        }
    }

    private fun handleLoading(b: Boolean) {
        try{
            if (b) {
                LoadingDialog.getProgressDialog(
                    requireContext(),
                    R.string.loading_dialog_text,
                    com.kronos.resources.R.color.colorSecondaryVariant
                )!!.show()
            } else {
                LoadingDialog.getProgressDialog(
                    requireContext(),
                    R.string.loading_dialog_text,
                    com.kronos.resources.R.color.colorSecondaryVariant
                )!!.dismiss()
            }
        }catch (e:Exception){}

    }


    private fun initViewModel() {
        val bundle = arguments
        if (bundle?.get(CURRENT_TYPE) != null) {
            viewModel.loadTypeInfo(bundle.get(CURRENT_TYPE) as NamedResourceApi)
        } else {
            findNavController().popBackStack()
        }
    }

    private fun initViews() {
        initDoubleDamageFromRecyclerView()
        initDoubleDamageToRecyclerView()
        initHalfDamageFromRecyclerView()
        initHalfDamageToRecyclerView()
        initNoDamageFromRecyclerView()
        initNoDamageToRecyclerView()
    }

    private fun initDoubleDamageFromRecyclerView(){
        binding.recyclerViewDoubleDamageFrom.layoutManager = GridLayoutManager(context,2)
        binding.recyclerViewDoubleDamageFrom.setHasFixedSize(false)
        if (viewModel.doubleDamageFromAdapter.get() == null)
            viewModel.doubleDamageFromAdapter = WeakReference(DamageRelationTypeAdapter())
        viewModel.doubleDamageFromAdapter.get()?.setUrlProvider(viewModel.urlProvider)
        binding.recyclerViewDoubleDamageFrom.adapter = viewModel.doubleDamageFromAdapter.get()
    }

    private fun initHalfDamageFromRecyclerView(){
        binding.recyclerViewHalfDamageFrom.layoutManager = GridLayoutManager(context,2)
        binding.recyclerViewHalfDamageFrom.setHasFixedSize(false)
        if (viewModel.halfDamageFromAdapter.get() == null)
            viewModel.halfDamageFromAdapter = WeakReference(DamageRelationTypeAdapter())
        viewModel.halfDamageFromAdapter.get()?.setUrlProvider(viewModel.urlProvider)
        binding.recyclerViewHalfDamageFrom.adapter = viewModel.halfDamageFromAdapter.get()
    }

    private fun initNoDamageFromRecyclerView(){
        binding.recyclerViewNoDamageFrom.layoutManager = GridLayoutManager(context,2)
        binding.recyclerViewNoDamageFrom.setHasFixedSize(false)
        if (viewModel.noDamageFromAdapter.get() == null)
            viewModel.noDamageFromAdapter = WeakReference(DamageRelationTypeAdapter())
        viewModel.noDamageFromAdapter.get()?.setUrlProvider(viewModel.urlProvider)
        binding.recyclerViewNoDamageFrom.adapter = viewModel.noDamageFromAdapter.get()
    }

    private fun initDoubleDamageToRecyclerView(){
        binding.recyclerViewDoubleDamageTo.layoutManager = GridLayoutManager(context,2)
        binding.recyclerViewDoubleDamageTo.setHasFixedSize(false)
        if (viewModel.doubleDamageToAdapter.get() == null)
            viewModel.doubleDamageToAdapter = WeakReference(DamageRelationTypeAdapter())
        viewModel.doubleDamageToAdapter.get()?.setUrlProvider(viewModel.urlProvider)
        binding.recyclerViewDoubleDamageTo.adapter = viewModel.doubleDamageToAdapter.get()
    }

    private fun initHalfDamageToRecyclerView(){
        binding.recyclerViewHalfDamageTo.layoutManager = GridLayoutManager(context,2)
        binding.recyclerViewHalfDamageTo.setHasFixedSize(false)
        if (viewModel.halfDamageToAdapter.get() == null)
            viewModel.halfDamageToAdapter = WeakReference(DamageRelationTypeAdapter())
        viewModel.halfDamageToAdapter.get()?.setUrlProvider(viewModel.urlProvider)
        binding.recyclerViewHalfDamageTo.adapter = viewModel.halfDamageToAdapter.get()
    }

    private fun initNoDamageToRecyclerView(){
        binding.recyclerViewNoDamageTo.layoutManager = GridLayoutManager(context,2)
        binding.recyclerViewNoDamageTo.setHasFixedSize(false)
        if (viewModel.noDamageToAdapter.get() == null)
            viewModel.noDamageToAdapter = WeakReference(DamageRelationTypeAdapter())
        viewModel.noDamageToAdapter.get()?.setUrlProvider(viewModel.urlProvider)
        binding.recyclerViewNoDamageTo.adapter = viewModel.noDamageToAdapter.get()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.screen_detail, menu)
        super.onCreateOptionsMenu(menu, inflater);
    }

    override fun onDestroyView() {
        binding.unbind()
        viewModel.doubleDamageFromAdapter = WeakReference(null)
        viewModel.halfDamageFromAdapter = WeakReference(null)
        viewModel.noDamageFromAdapter = WeakReference(null)
        viewModel.doubleDamageToAdapter = WeakReference(null)
        viewModel.halfDamageToAdapter = WeakReference(null)
        viewModel.noDamageToAdapter = WeakReference(null)

        super.onDestroyView()
    }

}


