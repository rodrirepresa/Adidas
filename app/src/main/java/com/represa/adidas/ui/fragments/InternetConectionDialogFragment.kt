package com.represa.adidas.ui.fragments

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextAlign
import androidx.fragment.app.DialogFragment
import com.represa.adidas.R
import com.represa.adidas.databinding.InternetConnectionDialogBinding
import com.represa.adidas.ui.MyTheme
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale


class InternetConectionDialogFragment : DialogFragment() {

    private var _binding: InternetConnectionDialogBinding? = null
    private val binding get() = _binding!!

    @ExperimentalAnimationApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog!!.window?.setBackgroundDrawable(ColorDrawable(0xE8FFFFFF.toInt()));
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

    override fun onResume() {
        super.onResume()
        dismissAnimation.value = false
    }

    override fun dismiss() {
        dismissAnimation.value = true
    }

    private val dismissAnimation = mutableStateOf(false)

    @ExperimentalAnimationApi
    private fun initCompose() {
        binding.apply {
            root.findViewById<ComposeView>(R.id.compose_view).setContent {
                MyTheme() {
                    val scale = remember { Animatable(1f) }
                    if (dismissAnimation.value) {
                        LaunchedEffect(scale) {
                            scale.animateTo(
                                targetValue = 2f,
                                animationSpec = tween(durationMillis = 200, easing = LinearEasing)
                            )
                            super.dismiss()
                        }
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .scale(scale.value)
                            .alpha(2 - scale.value),
                        contentAlignment = Alignment.Center,
                    ) {
                        Column(
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
}
