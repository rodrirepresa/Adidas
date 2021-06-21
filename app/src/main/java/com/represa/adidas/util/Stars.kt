package com.represa.adidas.util

import androidx.compose.animation.core.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.represa.adidas.ui.viewmodels.ProductDetailViewModel
import kotlinx.coroutines.launch

@Composable
fun Rate(productDetailViewModel: ProductDetailViewModel) {
    var starData = remember { StarData() }
    Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.height(40.dp)) {
        for (i in 1..5) {
            star(position = i, starData = starData, productDetailViewModel = productDetailViewModel)
        }
    }
}

@Composable
fun star(position: Int, starData: StarData, productDetailViewModel: ProductDetailViewModel) {

    var currentState by remember {
        mutableStateOf(StarState.IDLE)
    }
    val transition = updateTransition(currentState, label = "")

    val starAlpha by transition.animateFloat(
        transitionSpec = {
            when {
                StarState.IDLE isTransitioningTo StarState.Selected ->
                    tween(1000)
                else ->
                    snap(0)
            }
        }
    ) { state ->
        when (state) {
            StarState.IDLE -> 0f
            StarState.Selected -> 1f
        }
    }

    currentState = if (starData.starList.contains(position)) {
        StarState.Selected
    } else {
        StarState.IDLE
    }


    var sizeAnimation = remember { Animatable(25f) }
    var coroutineScope = rememberCoroutineScope()

    Box(modifier = Modifier.padding(10.dp)) {
        Icon(Icons.Default.StarOutline, contentDescription = "", modifier = Modifier
            .size(sizeAnimation.value.dp)
            .clickable { starData.click(position) })

        Icon(Icons.Default.Star, contentDescription = "", modifier = Modifier
            .size(sizeAnimation.value.dp)
            .clickable {
                starData.click(position)
                productDetailViewModel.setRating(position)
                coroutineScope.launch {
                    sizeAnimation.animateTo(
                        targetValue = 30f,
                        animationSpec = tween(durationMillis = 200, easing = LinearEasing)
                    )
                    sizeAnimation.animateTo(
                        targetValue = 25f,
                        animationSpec = tween(durationMillis = 500, easing = LinearEasing)
                    )
                }
            }
            .alpha(starAlpha)
        )
    }
}

private enum class StarState {
    Selected,
    IDLE
}

class StarData() {
    var starList = mutableStateListOf<Int>(1,2,3)

    fun click(star: Int) {
        var list = mutableListOf<Int>()
        for (i in 1..star) {
            list.add(i)
        }
        starList.clear()
        starList.addAll(list)
    }
}