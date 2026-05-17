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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.teddynotes.R
import com.example.teddynotes.navigation.NavRoutes
import com.example.teddynotes.ui.theme.BackgroundGreen
import com.example.teddynotes.ui.theme.BearPrimary
import com.example.teddynotes.ui.theme.LobsterTwo
import com.example.teddynotes.ui.theme.NoteBeige
import com.example.teddynotes.ui.theme.PrimaryTextBrown
import com.example.teddynotes.ui.theme.SoftWhite
import com.example.teddynotes.viewmodel.HomeViewModel
import com.example.teddynotes.viewmodel.NoteViewModel
import com.example.teddynotes.viewmodel.UserViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavController,
    noteViewModel: NoteViewModel,
    homeViewModel: HomeViewModel,
    userViewModel: UserViewModel
) {

    val isOnboarded by userViewModel.isOnboarded.collectAsState()
    val isLoading by userViewModel.isLoading.collectAsState()
    if (!isLoading && !isOnboarded) {
        OnboardingDialog(
            onComplete = { username, dob, email, gender ->
                userViewModel.saveUser(username, dob, email, gender)
            }
        )
    }
    val username by userViewModel.username.collectAsState()
    val dpUri by userViewModel.dpUri.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    var quote = homeViewModel.quote.collectAsState()
    val morningGreetings = listOf(
        "Good morning, ${username} ☀️",
        "A fresh day to breathe and grow 🌿",
        "Hope today feels gentle for you 🤎"
    )

    val afternoonGreetings = listOf(
        "How’s your day going so far? ✨",
        "Take a little pause for yourself 🌸",
        "Glad to see you back 🌿"
    )

    val eveningGreetings = listOf(
        "Welcome back ${username} 🌙",
        "Time to unwind a little 🤎",
        "Another note for today 📖"
    )

    val hour = java.time.LocalTime.now().hour

    val greeting = remember(username) {
        if (username.isEmpty()) ""
        else {
            when (hour) {
                in 5..11 -> morningGreetings.random()
                in 12..16 -> afternoonGreetings.random()
                else -> eveningGreetings.random()
            }
        }
    }

    Scaffold(containerColor = BackgroundGreen) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            TextBlock(greeting)
            Spacer(Modifier.height(48.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
            ) {
                HomeCard(onClick = { navController.navigate(NavRoutes.ProfileScreen) }) {
                    if (dpUri.isNotEmpty()) {
                        AsyncImage(
                            model = java.io.File(dpUri),
                            contentDescription = "DP",
                            modifier = Modifier
                                .size(120.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Image(
                            painter = painterResource(R.drawable.user_dp),
                            contentDescription = "user dp",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .clip(CircleShape)
                                .fillMaxSize()
                        )
                    }
                }
                HomeCard(onClick = { navController.navigate((NavRoutes.NotesListScreen)) }) {
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
                    val today = java.time.LocalDate.now()
                        .format(java.time.format.DateTimeFormatter.ofPattern("dd MMM yyyy"))

                    coroutineScope.launch {
                        val existingNote = noteViewModel.getNoteByDate(today)
                        if (existingNote != null) {
                            navController.navigate(NavRoutes.NoteScreen(existingNote.date))
                        } else {
                            navController.navigate(NavRoutes.NoteScreen())

                        }
                    }

                }) {
                    Icon(
                        imageVector = Icons.Outlined.AddCircleOutline,
                        contentDescription = "add note",
                        modifier = Modifier.fillMaxSize(),
                        tint = BearPrimary
                    )
                }

            }
            Spacer(Modifier.height(48.dp))
            TextBlock(quote.value)
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
            fontSize = 34.sp,
            lineHeight = 40.sp,
            color = PrimaryTextBrown
        )
    }
}
