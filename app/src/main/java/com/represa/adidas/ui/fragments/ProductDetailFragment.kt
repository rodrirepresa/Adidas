package com.represa.adidas.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.represa.adidas.databinding.FragmentProductDetailBinding
import com.represa.adidas.ui.viewmodels.ProductDetailViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import com.represa.adidas.ui.adapters.ReviewsAdapter


class ProductDetailFragment : Fragment() {

    private val productDetailViewModel by sharedViewModel<ProductDetailViewModel>()

    private val safeArgs: ProductDetailFragmentArgs by navArgs()
    private var productId: String = ""
    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!

    private fun startObservers() {
        productDetailViewModel.product.observe(viewLifecycleOwner, {
            with(binding) {
                productImage.load(it.imgUrl)
                bottomSheet.productName.text = it.name.toUpperCase()
                bottomSheet.productPrice.text = it.price.toString() + it.currency
                bottomSheet.productDescription.text = it.description
            }
        })
        productDetailViewModel.getReviews(productId).observe(viewLifecycleOwner, {
            with(binding.bottomSheet) {
                var adapter = recyclerReviews.adapter as ReviewsAdapter
                adapter.submitList(it)
                if (it.isEmpty()) {
                    noReviews.visibility = View.VISIBLE
                    writeReview.visibility = View.VISIBLE
                    textReview.visibility = View.GONE
                } else {
                    noReviews.visibility = View.GONE
                    writeReview.visibility = View.GONE
                    textReview.visibility = View.VISIBLE
                }
            }
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

        with(binding) {
            background.setBackgroundColor(0xFFeceeef.toInt())
            review.setOnClickListener {
                openReviewDialog()
            }
            bottomSheet.writeReview.setOnClickListener {
                openReviewDialog()
            }
            cardview.setOnClickListener {
                ProductDetailFragment@ findNavController().popBackStack()
            }
        }

        val adapter = createAdapter()
        setUpRecyclerView(adapter)

        startObservers()

        return binding.root
    }

    private fun openReviewDialog() {
        var dialog = ReviewDialogFragment()
        var bundle = Bundle()
        bundle.putString("productId", productId)
        dialog.arguments = bundle
        dialog.show(parentFragmentManager, "MyCustomFragment")
    }

    private fun createAdapter(): ReviewsAdapter {
        return ReviewsAdapter()
    }

    private fun setUpRecyclerView(adapter: ReviewsAdapter) {
        binding.bottomSheet.recyclerReviews.adapter = adapter
    }

}