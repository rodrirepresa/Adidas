package com.represa.adidas.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.findNavController
import com.represa.adidas.R
import com.represa.adidas.databinding.FragmentSplashBinding
import com.represa.adidas.databinding.ReviewDialogBinding
import com.represa.adidas.ui.MyTheme
import com.represa.adidas.util.RateStars
import com.represa.adidas.util.SplashScreen

class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSplashBinding.inflate(layoutInflater, container, false)
        initCompose()
        return binding.root
    }

    private fun initCompose() {
        val action = SplashFragmentDirections.actionSplashFragmentToProductsFragment()
        binding.apply {
            root.findViewById<ComposeView>(R.id.compose_view).setContent {
                MyTheme() {
                    SplashScreen {
                        findNavController().navigate(action)
                    }
                }
            }

        }
    }

}