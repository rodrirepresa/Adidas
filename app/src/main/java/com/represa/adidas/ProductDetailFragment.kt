package com.represa.adidas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import com.represa.adidas.databinding.FragmentProductDetailBinding
import com.represa.adidas.ui.ReviewDialog
import com.represa.adidas.ui.viewmodels.ProductDetailViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProductDetailFragment : Fragment() {

    private val productDetailViewModel by sharedViewModel<ProductDetailViewModel>()

    private val safeArgs: ProductDetailFragmentArgs by navArgs()
    private var productId : String = ""
    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!

    private fun startObservers() {
        productDetailViewModel.product.observe(viewLifecycleOwner, {
            binding.name.text = it.name
        })
        productDetailViewModel.getReviews(productId).observe(viewLifecycleOwner, {
            Toast.makeText(context, it.size.toString(), Toast.LENGTH_SHORT).show()
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        productId = safeArgs.productId
        productDetailViewModel.getProduct(productId)
        productDetailViewModel.fetchReviews(productId)

        binding.review.setOnClickListener {
            var dialog = ReviewDialog()
            var bundle = Bundle()
            bundle.putString("productId", productId)
            dialog.arguments = bundle
            dialog.show(parentFragmentManager, "MyCustomFragment")
        }

        startObservers()

        return binding.root
    }
}