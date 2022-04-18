package com.gielow.cleanbaseapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.gielow.cleanbaseapp.R

@Composable
fun LoadingScreen() {
    val animation by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(
            R.raw.crypto_loader
        )
    )
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(
            composition = animation,
            iterations = LottieConstants.IterateForever
        )
    }
}

@Preview
@Composable
private fun Preview() {
    LoadingScreen()
}