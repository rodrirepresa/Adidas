package com.represa.adidas

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.represa.adidas.databinding.FragmentProductsBinding
import com.represa.adidas.ui.adapters.ProductsAdapter
import com.represa.adidas.ui.viewmodels.ProductViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ProductsFragment : Fragment() {

    private val productViewModel by sharedViewModel<ProductViewModel>()
    private var _binding: FragmentProductsBinding? = null

    private val binding get() = _binding!!

    private fun startObservers() {
        productViewModel.productSearched.observe(viewLifecycleOwner,  {
            var adapter = binding.productList.adapter as ProductsAdapter
            adapter.submitList(it)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        startObservers()

        binding.searchText.addTextChangedListener( object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
                productViewModel.updateSearchQuery(s.toString())
            }
        })

        //Set up Photo adapter
        val adapter = createAdapter()
        setUpRecyclerView(adapter)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun createAdapter(): ProductsAdapter {
        return ProductsAdapter {
            val action = ProductsFragmentDirections.actionProductsFragmentToProductDetailFragment(it)
            view?.findNavController()!!.navigate(action)
        }
    }

    private fun setUpRecyclerView(adapter: ProductsAdapter){
        binding.productList.adapter = adapter
    }

}