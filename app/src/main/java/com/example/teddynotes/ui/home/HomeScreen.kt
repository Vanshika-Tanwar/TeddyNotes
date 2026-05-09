package com.example.teddynotes.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircleOutline
import androidx.compose.material.icons.outlined.Notes
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.teddynotes.R
import com.example.teddynotes.navigation.NavRoutes
import com.example.teddynotes.ui.theme.BackgroundGreen
import com.example.teddynotes.ui.theme.BearPrimary
import com.example.teddynotes.ui.theme.LobsterTwo
import com.example.teddynotes.ui.theme.NoteBeige
import com.example.teddynotes.ui.theme.PrimaryTextBrown
import com.example.teddynotes.ui.theme.SoftWhite

@Composable
fun HomeScreen(navController: NavController) {
    val user = "Vanshika"
    Scaffold(containerColor = BackgroundGreen) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            TextBlock("Hello ${user}!")
            Spacer(Modifier.height(32.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
            ) {
                HomeCard (onClick = {navController.navigate((NavRoutes.NotesList))}){
                    Image(
                        painter = painterResource(R.drawable.user_dp),
                        contentDescription = "user dp",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(CircleShape)
                            .fillMaxSize()

                    )

                }
                HomeCard(onClick = {navController.navigate((NavRoutes.NotesList))}){
                    Icon(
                        imageVector = Icons.Outlined.Notes,
                        contentDescription = "notes list",
                        modifier = Modifier.fillMaxSize(),
                        tint = BearPrimary
                    )
                }

            }
            Spacer(Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
            ) {
                HomeCard(onClick = {
                    navController.navigate(NavRoutes.ChatBotScreen)
                }) {
                    Image(
                        painter = painterResource(R.drawable.bear_logo),
                        contentDescription = "chatbot",
                        modifier = Modifier.fillMaxSize()

                    )

                }
                HomeCard(onClick = {
                    navController.navigate(NavRoutes.NoteScreen)
                }) {
                    Icon(
                        imageVector = Icons.Outlined.AddCircleOutline,
                        contentDescription = "add note",
                        modifier = Modifier.fillMaxSize(),
                        tint = BearPrimary
                    )
                }

            }
            Spacer(Modifier.height(32.dp))
            TextBlock("\"The secret of getting ahead is getting started.\"")
        }

    }
}

@Composable
fun HomeCard(
    onClick: () -> Unit = {},
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .size(160.dp)
            .clip(RoundedCornerShape(24.dp))
            .clickable(onClick = onClick)
            .background(NoteBeige)
            .border(
                1.dp,
                NoteBeige.copy(alpha = 0.5f),
                RoundedCornerShape(24.dp)
            )
            .padding(16.dp),
        contentAlignment = Alignment.Center,
        content = content
    )
}

@Composable
fun TextBlock(
    text: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(SoftWhite.copy(alpha = 0.65f))
            .border(
                1.dp,
                SoftWhite.copy(alpha = 0.2f),
                RoundedCornerShape(24.dp)
            )
            .padding(16.dp)
    ) {
        Text(
            text = text,
            fontFamily = LobsterTwo,
            fontSize = 48.sp,
            lineHeight = 56.sp,
            color = PrimaryTextBrown
        )
    }
}

