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
import androidx.compose.animation.core.tween
import androidx.compose.runtime.LaunchedEffect
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
        dismissAnimation.value = animationState.ENTER
    }

    override fun dismiss() {
        dismissAnimation.value = animationState.EXIT
    }

    private val dismissAnimation = mutableStateOf(animationState.IDLE)

    private enum class animationState {
        IDLE,
        ENTER,
        EXIT
    }

    @ExperimentalAnimationApi
    private fun initCompose() {
        binding.apply {
            root.findViewById<ComposeView>(R.id.compose_view).setContent {
                MyTheme() {
                    val scale = remember { Animatable(0f) }
                    when (dismissAnimation.value) {
                        animationState.ENTER -> {
                            LaunchedEffect(scale) {
                                scale.animateTo(
                                    targetValue = 1f,
                                    animationSpec = tween(
                                        durationMillis = 200,
                                        easing = LinearEasing
                                    )
                                )
                            }
                        }
                        animationState.EXIT -> {
                            LaunchedEffect(scale) {
                                scale.animateTo(
                                    targetValue = 2f,
                                    animationSpec = tween(
                                        durationMillis = 200,
                                        easing = LinearEasing
                                    )
                                )
                                super.dismiss()
                            }
                        }

                    }
                    var alpha = if (dismissAnimation.value == animationState.ENTER) {
                        scale.value
                    } else {
                        2 - scale.value
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .scale(scale.value)
                            .alpha(alpha),
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
