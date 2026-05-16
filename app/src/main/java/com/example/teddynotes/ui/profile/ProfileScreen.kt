package com.example.teddynotes.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.teddynotes.R
import com.example.teddynotes.navigation.NavRoutes
import com.example.teddynotes.ui.common.TeddyTopBar
import com.example.teddynotes.ui.theme.BackgroundGreen
import com.example.teddynotes.ui.theme.BearPrimary
import com.example.teddynotes.ui.theme.MoodSad
import com.example.teddynotes.ui.theme.NoteBeige
import com.example.teddynotes.ui.theme.Nunito
import com.example.teddynotes.ui.theme.PrimaryTextBrown
import com.example.teddynotes.ui.theme.SoftWhite
import com.example.teddynotes.viewmodel.NoteViewModel
import com.example.teddynotes.viewmodel.UserViewModel

@Composable
fun ProfileScreen(navController: NavController, noteViewModel: NoteViewModel, userViewModel: UserViewModel) {

    val username by userViewModel.username.collectAsState()
    val dob by userViewModel.dob.collectAsState()
    val email by userViewModel.email.collectAsState()
    val gender by userViewModel.gender.collectAsState()
    val age = remember(dob) {
        if (dob.isNotEmpty()) {
            try {
                val birthDate = java.time.LocalDate.parse(dob,
                    java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                java.time.Period.between(birthDate, java.time.LocalDate.now()).years.toString()
            } catch (e: Exception) { "" }
        } else ""
    }
    val notes by noteViewModel.allNotes.collectAsState()

    Scaffold(containerColor = BackgroundGreen) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            TeddyTopBar(onBack = { navController.popBackStack() })
            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 8.dp, bottom = 20.dp)
                    .clip(shape = RoundedCornerShape(24.dp))
                    .background(SoftWhite.copy(alpha = 0.65f))
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(contentAlignment = Alignment.BottomEnd, modifier = Modifier.padding(8.dp)) {
                        Image(
                            painter = painterResource(R.drawable.user_dp),
                            contentDescription = "Profile Photo",
                            modifier = Modifier
                                .size(120.dp)
                                .clip(CircleShape)
                        )
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(NoteBeige.copy(alpha = 0.9f))
                                .padding(6.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit Profile",
                                tint = BearPrimary,
                                modifier = Modifier.fillMaxSize()
                            )
                        }

                    }
                    InfoCard("Name: $username")
                    InfoCard("Age: $age")
                    InfoCard("Email: $email")
                    InfoCard("Gender: $gender")

                    InfoCard("Total Notes: ${notes.size} notes")
                    InfoCard("Mood This Week: 😊 Happy")

                    InfoCard("View all notes", onClick = {
                        navController.navigate(NavRoutes.NotesListScreen)
                    })

                    Button(
                        onClick = { /* delete logic later */ },
                        colors = ButtonDefaults.buttonColors(containerColor = MoodSad),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            "DELETE ACCOUNT? 🥲",
                            fontFamily = Nunito,
                            color = PrimaryTextBrown,
                            fontSize = 24.sp
                        )
                    }

                }
            }
        }
    }
}

@Composable
fun InfoCard(text: String, onClick: (() -> Unit)? = null) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(NoteBeige)
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier)
            .padding(16.dp)
    ) {
        Text(text, fontFamily = Nunito, fontSize = 18.sp, color = PrimaryTextBrown)
    }
}