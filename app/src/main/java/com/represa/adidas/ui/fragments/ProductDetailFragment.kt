package com.represa.adidas.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import coil.load
import com.represa.adidas.databinding.FragmentProductDetailBinding
import com.represa.adidas.ui.viewmodels.ProductDetailViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

import com.represa.adidas.ui.adapters.ReviewsAdapter


class ProductDetailFragment : Fragment() {

    private val productDetailViewModel by sharedViewModel<ProductDetailViewModel>()

    private val safeArgs: com.represa.adidas.ui.fragments.ProductDetailFragmentArgs by navArgs()
    private var productId: String = ""
    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!

    private fun startObservers() {
        productDetailViewModel.product.observe(viewLifecycleOwner, {
            binding.productImage.load(it.imgUrl)
            binding.bottomSheet.productName.text = it.name.toUpperCase()
            binding.bottomSheet.productPrice.text = it.price.toString() + it.currency
            binding.bottomSheet.productDescription.text = it.description
            /*productDetailViewModel.setUpBackgroundColor(it.imgUrl){ palette ->
                palette?.let {
                    var rgb = palette.swatches.first()
                    //binding.background.setBackgroundColor(rgb.rgb)
                }
            }*/
        })
        productDetailViewModel.getReviews(productId).observe(viewLifecycleOwner, {
            var adapter = binding.bottomSheet.recyclerReviews.adapter as ReviewsAdapter
            adapter.submitList(it)
            if (it.isEmpty()) {
                binding.bottomSheet.noReviews.visibility = View.VISIBLE
                binding.bottomSheet.writeReview.visibility = View.VISIBLE
                binding.bottomSheet.textReview.visibility = View.GONE
            } else {
                binding.bottomSheet.noReviews.visibility = View.GONE
                binding.bottomSheet.writeReview.visibility = View.GONE
                binding.bottomSheet.textReview.visibility = View.VISIBLE
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
        productDetailViewModel.fetchReviews(productId)

        binding.background.setBackgroundColor(0xFFeceeef.toInt())

        binding.review.setOnClickListener {
            openReviewDialog()
        }

        binding.bottomSheet.writeReview.setOnClickListener{
            openReviewDialog()
        }

        binding.cardview.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        //Set up Review adapter
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