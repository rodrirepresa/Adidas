package com.represa.adidas.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.represa.adidas.databinding.FragmentProductsBinding
import com.represa.adidas.ui.adapters.ProductsAdapter
import com.represa.adidas.ui.viewmodels.ProductViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.fragment.app.commit
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.represa.adidas.R
import com.represa.adidas.ui.MyTheme
import com.represa.adidas.ui.other.GridLayoutItemDecoration

class ProductsFragment : Fragment() {

    private val productViewModel by sharedViewModel<ProductViewModel>()
    private var _binding: FragmentProductsBinding? = null

    private val binding get() = _binding!!

    private fun startObservers() {
        productViewModel.productSearched.observe(viewLifecycleOwner, {
            if (it.isNotEmpty()) {
                binding.noResults.visibility = View.GONE
                binding.yourSearch.visibility = View.GONE
            } else {
                binding.noResults.visibility = View.VISIBLE
                binding.yourSearch.text =
                    "Your search " + productViewModel.searchFlow.value + " did not match any product"
                binding.yourSearch.visibility = View.VISIBLE
            }
            var adapter = binding.productList.adapter as ProductsAdapter
            adapter.submitList(it)
        })
    }

    @ExperimentalAnimationApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        startObservers()

        with(binding.productList) {
            (layoutManager as GridLayoutManager).spanSizeLookup =
                object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return when (position % 9) {
                            0, 1, 3, 4, 5, 6 -> 1
                            else -> 2
                        }
                    }
                }
            addItemDecoration(GridLayoutItemDecoration(15))
            addOnScrollListener(object : OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!(dx == 0 && dy == 0)) {
                        productViewModel.showTitle.value = false
                    }
                }

            })
        }


        initCompose()

        //Set up Product adapter
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
            val action =
                ProductsFragmentDirections.actionProductsFragmentToProductDetailFragment(
                    it
                )
            view?.findNavController()!!.navigate(action)
        }
    }

    private fun setUpRecyclerView(adapter: ProductsAdapter) {
        binding.productList.adapter = adapter
    }

    @ExperimentalAnimationApi
    private fun initCompose() {
        binding.apply {
            root.findViewById<ComposeView>(R.id.compose_view).setContent {
                // In Compose world
                MyTheme() {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .background(Color(0xFFFCFCFF))
                    ) {
                        var showTitle by remember { productViewModel.showTitle }
                        AnimatedVisibility(visible = showTitle) {
                            Text(
                                modifier = Modifier.padding(30.dp, 20.dp, 30.dp, 0.dp),
                                text = "New\nProducts",
                                style = MaterialTheme.typography.h1,
                                color = MaterialTheme.colors.primary
                            )
                        }
                        var searchedText = remember { mutableStateOf("") }
                        var colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = Color.Gray,
                            textColor = Color.DarkGray
                        )
                        OutlinedTextField(
                            trailingIcon = {
                                Icon(
                                    Icons.Filled.RemoveCircleOutline,
                                    "",
                                    modifier = Modifier
                                        .clickable {
                                            searchedText.value = ""
                                            productViewModel.updateSearchQuery("")
                                        }
                                        .size(17.dp)
                                )
                            },
                            colors = colors,
                            textStyle = MaterialTheme.typography.body1,
                            maxLines = 1,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(30.dp, 10.dp, 30.dp, 10.dp)
                                .height(50.dp)
                                .background(Color.White, MaterialTheme.shapes.small),
                            value = searchedText.value,
                            onValueChange = { it ->
                                showTitle = false
                                if (it.length < 30) {
                                    searchedText.value = it
                                    productViewModel.updateSearchQuery(it)
                                }
                            },
                            placeholder = {
                                Text(
                                    text = "Search articles and description",
                                    style = MaterialTheme.typography.body1,
                                    color = Color.LightGray
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}
