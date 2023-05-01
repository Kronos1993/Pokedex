package com.kronos.pokedex.ui.show_image

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.kronos.core.extensions.binding.fragmentBinding
import com.kronos.pokedex.R
import com.kronos.pokedex.databinding.FragmentDialogShowImageBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val CURRENT_IMAGE_URL = "current_image_url"

@AndroidEntryPoint
class ShowImageDialogFragment : DialogFragment() {
    private val binding by fragmentBinding<FragmentDialogShowImageBinding>(R.layout.fragment_dialog_show_image)
    private val viewModel by viewModels<ShowImageDialogViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.run {
        lifecycleOwner = this@ShowImageDialogFragment.viewLifecycleOwner
        setObservers()
        root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpDialog()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(true)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

    private fun setObservers() {
        viewModel.imageUrl.observe(this.viewLifecycleOwner, ::handleImageUrl)
    }

    private fun handleImageUrl(url: String) {
        Glide.with(requireContext()).load(url).placeholder(R.drawable.ic_pokeball)
            .into(binding.showImageDialogImageView)
        //binding.showImageDialogImageView.setOnTouchListener(ImageMatrixTouchHandler(binding.showImageDialogImageView.context));
    }

    private fun setUpDialog() {
        this.isCancelable = true
        val bundle = arguments
        if (bundle?.get(CURRENT_IMAGE_URL) != null) {
            viewModel.postImageUrl(bundle.get(CURRENT_IMAGE_URL) as String)
            initViews()
        } else {
            hideDialog()
        }
        binding.buttonClose.setOnClickListener { hideDialog() }
    }

    private fun initViews() {
        if (!viewModel.imageUrl.value.isNullOrEmpty())
            Glide.with(requireContext()).load(viewModel.imageUrl.value).placeholder(R.drawable.ic_pokeball)
                .into(binding.showImageDialogImageView)
    }

    private fun hideDialog() {
        lifecycleScope.launch {
            withContext(Dispatchers.Main) {
                findNavController().navigateUp()
            }
        }
    }

    override fun onDestroy() {
        binding.unbind()
        super.onDestroy()
    }
}