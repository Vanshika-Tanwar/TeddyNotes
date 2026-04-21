package com.example.teddynotes.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieConstants
import com.example.teddynotes.ui.theme.BackgroundGreen

@Composable
fun HomeScreen(navController: NavController){
    Scaffold(containerColor = BackgroundGreen) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Text("HOME SCREEN", fontWeight = FontWeight.Bold, fontSize = 50.sp)

        }

    }
}