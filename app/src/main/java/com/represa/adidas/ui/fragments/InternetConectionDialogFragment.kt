package com.represa.adidas.ui.fragments

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.view.Window
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SignalWifiConnectedNoInternet4
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextAlign
import androidx.fragment.app.DialogFragment
import com.represa.adidas.R
import com.represa.adidas.databinding.InternetConnectionDialogBinding
import com.represa.adidas.ui.MyTheme


class InternetConectionDialogFragment : DialogFragment() {

    private var _binding: InternetConnectionDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog!!.window?.setBackgroundDrawable(ColorDrawable(0xE6FFFFFF.toInt()));
        _binding = InternetConnectionDialogBinding.inflate(layoutInflater, container, false)
        isCancelable = false
        initCompose()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels)
        val height = (resources.displayMetrics.heightPixels)
        dialog!!.window?.setLayout(width, height)

    }

    private fun initCompose() {
        binding.apply {
            root.findViewById<ComposeView>(R.id.compose_view).setContent {
                MyTheme() {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(Icons.Default.WifiOff, "")
                        Text(
                            text = "THE SERVER IS NOT AVAILABLE",
                            style = MaterialTheme.typography.h2,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "Please verify that you are connected to\nthe internet.",
                            style = MaterialTheme.typography.body1,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

        }
    }
}