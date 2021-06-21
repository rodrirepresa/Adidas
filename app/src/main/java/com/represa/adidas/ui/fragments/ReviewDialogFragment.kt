package com.represa.adidas.ui.fragments

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.represa.adidas.databinding.ReviewDialogBinding
import com.represa.adidas.ui.viewmodels.ProductDetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReviewDialogFragment : DialogFragment() {

    private val productDetailViewModel by viewModel<ProductDetailViewModel>()

    private var _binding: ReviewDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        _binding = ReviewDialogBinding.inflate(layoutInflater, container, false)
        var productId = arguments?.getString("productId")
        binding.submit.setOnClickListener {
            binding.text.editText?.let {
                if (it.text.isNotEmpty()) {
                    productDetailViewModel.createReview(
                        productId,
                        binding.text.editText!!.text.toString()
                    ) { dismiss() }
                } else {
                    it.error = "Empty review"
                }
            }
        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, height)
    }
}