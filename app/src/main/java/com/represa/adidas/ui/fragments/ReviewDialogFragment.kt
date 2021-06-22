package com.represa.adidas.ui.fragments

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextAlign
import androidx.fragment.app.DialogFragment
import com.represa.adidas.R
import com.represa.adidas.databinding.ReviewDialogBinding
import com.represa.adidas.ui.MyTheme
import com.represa.adidas.ui.viewmodels.ProductDetailViewModel
import com.represa.adidas.util.RateStars
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

        dialog!!.window!!.setGravity(Gravity.CENTER_HORIZONTAL)
        var p = dialog!!.window!!.attributes
        p.width = ViewGroup.LayoutParams.MATCH_PARENT
        p.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING
        p.y = -200
        dialog!!.window!!.attributes = p

        initCompose()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, height)
    }

    private fun initCompose() {
        binding.apply {
            root.findViewById<ComposeView>(R.id.compose_view).setContent {
                MyTheme() {
                    RateStars(productDetailViewModel)
                }
            }

        }
    }
}