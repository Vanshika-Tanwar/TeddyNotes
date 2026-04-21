package com.example.teddynotes.ui.splash

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.teddynotes.R
import com.example.teddynotes.navigation.NavRoutes
import com.example.teddynotes.ui.theme.BackgroundGreen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController : NavController) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.bear))

    LaunchedEffect(Unit) {
        delay(3000)
        navController.navigate(NavRoutes.HomeScreen){
            popUpTo(NavRoutes.SplashScreen) { inclusive = true }
        }

    }
    Scaffold(containerColor = BackgroundGreen) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            LottieAnimation(composition, iterations = LottieConstants.IterateForever)

        }

    }
}