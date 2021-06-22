package com.represa.adidas.util


import androidx.compose.animation.core.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import com.represa.adidas.ui.viewmodels.ProductDetailViewModel
import kotlinx.coroutines.launch

@Composable
fun RateStars(productDetailViewModel: ProductDetailViewModel) {
    var starData = remember { StarData(productDetailViewModel) }
    Row(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(0.dp, 5.dp, 0.dp, 10.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.Bottom
    ) {
        for (i in 1..5) {
            star(position = i, starData = starData)
        }
    }
}

@Composable
fun star(position: Int, starData: StarData) {

    var scale = remember { Animatable(1f) }
    var coroutineScope = rememberCoroutineScope()

    var showFilled = starData.starList.contains(position)


    Box(modifier = Modifier.padding(10.dp,0.dp)) {
        Icon(Icons.Default.StarOutline, contentDescription = "", modifier = Modifier
            .scale(scale.value)
            .clickable {
                starData.click(position)
                starData.click(position)
                coroutineScope.launch {
                    scale.animateTo(
                        targetValue = 1.7f,
                        animationSpec = tween(durationMillis = 200, easing = LinearEasing)
                    )
                    scale.animateTo(
                        targetValue = 1f,
                        animationSpec = tween(durationMillis = 200, easing = LinearEasing)
                    )
                }
            })

        if (showFilled) {
            Icon(Icons.Default.Star, contentDescription = "", modifier = Modifier
                .scale(scale.value)
                .clickable {
                    starData.click(position)
                    coroutineScope.launch {
                        scale.animateTo(
                            targetValue = 1.7f,
                            animationSpec = tween(
                                durationMillis = 200,
                                easing = LinearEasing
                            )
                        )
                        scale.animateTo(
                            targetValue = 1f,
                            animationSpec = tween(
                                durationMillis = 200,
                                easing = LinearEasing
                            )
                        )
                    }
                }
            )
        }
    }
}

class StarData(var productDetailViewModel: ProductDetailViewModel) {
    var starList = mutableStateListOf<Int>()

    fun click(position: Int) {
        productDetailViewModel.setRating(position)
        var list = mutableListOf<Int>()
        for (i in 1..position) {
            list.add(i)
        }
        starList.clear()
        starList.addAll(list)
    }
}