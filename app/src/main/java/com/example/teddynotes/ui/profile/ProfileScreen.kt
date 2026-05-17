package com.example.teddynotes.ui.profile

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.teddynotes.R
import com.example.teddynotes.model.Mood
import com.example.teddynotes.navigation.NavRoutes
import com.example.teddynotes.ui.common.TeddyTopBar
import com.example.teddynotes.ui.home.GenderDropdown
import com.example.teddynotes.ui.home.OnboardingField
import com.example.teddynotes.ui.theme.BackgroundGreen
import com.example.teddynotes.ui.theme.BearDeep
import com.example.teddynotes.ui.theme.BearPrimary
import com.example.teddynotes.ui.theme.LobsterTwo
import com.example.teddynotes.ui.theme.MoodSad
import com.example.teddynotes.ui.theme.NoteBeige
import com.example.teddynotes.ui.theme.Nunito
import com.example.teddynotes.ui.theme.PrimaryTextBrown
import com.example.teddynotes.ui.theme.SoftWhite
import com.example.teddynotes.viewmodel.NoteViewModel
import com.example.teddynotes.viewmodel.UserViewModel

@Composable
fun ProfileScreen(
    navController: NavController, noteViewModel: NoteViewModel, userViewModel: UserViewModel
) {
    val context = LocalContext.current
    val username by userViewModel.username.collectAsState()
    val dob by userViewModel.dob.collectAsState()
    val email by userViewModel.email.collectAsState()
    val gender by userViewModel.gender.collectAsState()
    val age = remember(dob) {
        if (dob.isNotEmpty()) {
            try {
                val birthDate = java.time.LocalDate.parse(
                    dob, java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")
                )
                java.time.Period.between(birthDate, java.time.LocalDate.now()).years.toString()
            } catch (e: Exception) {
                ""
            }
        } else ""
    }
    val notes by noteViewModel.allNotes.collectAsState()
    var moodThisWeek by remember { mutableStateOf<Mood?>(null) }
    LaunchedEffect(Unit) {
        moodThisWeek = noteViewModel.getMoodThisWeek()
    }
    var showDeleteConfirm by remember { mutableStateOf(false) }
    var isEditing by remember { mutableStateOf(false) }
    var editName by remember { mutableStateOf(username) }
    var editDob by remember { mutableStateOf(dob) }
    var editEmail by remember { mutableStateOf(email) }
    var editGender by remember { mutableStateOf(gender) }
    val dpUri by userViewModel.dpUri.collectAsState()
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            val inputStream = context.contentResolver.openInputStream(it)
            val file = java.io.File(context.filesDir, "user_dp.jpg")
            inputStream?.use { input ->
                file.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
            val internalUri = file.absolutePath
            userViewModel.updateUser(editName, editDob, editEmail, editGender, internalUri)
        }
    }
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis =
            try {
            java.time.LocalDate.parse(editDob,
                java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                .atStartOfDay(java.time.ZoneId.of("UTC"))
                .toInstant()
                .toEpochMilli()
        } catch (e: Exception) {
            java.time.LocalDate.of(2000, 1, 1)
                .atStartOfDay(java.time.ZoneId.of("UTC"))
                .toInstant().toEpochMilli()
        }
    )

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val localDate = java.time.Instant.ofEpochMilli(millis)
                            .atZone(java.time.ZoneId.systemDefault())
                            .toLocalDate()
                        editDob = localDate.format(
                            java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")
                        )
                    }
                    showDatePicker = false
                }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) { Text("Cancel") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
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
                        if (dpUri.isNotEmpty()) {
                            AsyncImage(
                                model = java.io.File(dpUri),
                                contentDescription = "DP",
                                modifier = Modifier.size(120.dp).clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Image(
                                painterResource(R.drawable.user_dp),
                                contentDescription = "DP",
                                modifier = Modifier
                                    .size(120.dp)
                                    .clip(CircleShape)
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(NoteBeige.copy(alpha = 0.9f))
                                .padding(6.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            IconButton(onClick = {
                                if (isEditing) {
                                    launcher.launch("image/*")
                                } else {
                                    editName = username
                                    editDob = dob
                                    editEmail = email
                                    editGender = gender
                                    isEditing = true
                                }
                            }) {
                                Icon(
                                    imageVector = if (isEditing) Icons.Default.Add else Icons.Default.Edit,
                                    contentDescription = if (isEditing) "Change DP" else "Edit Profile",
                                    tint = BearPrimary,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }

                    }
                    if (isEditing) {
                        Button(
                            onClick = {
                                userViewModel.updateUser(
                                    editName,
                                    editDob,
                                    editEmail,
                                    editGender,
                                    dpUri
                                )
                                isEditing = false
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = BearDeep),
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Save Changes", fontFamily = Nunito, color = NoteBeige)
                        }
                        OnboardingField("Name", editName) { editName = it }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(NoteBeige)
                                .clickable { showDatePicker = true }
                                .padding(horizontal = 16.dp, vertical = 12.dp)
                        ) {
                            Text(
                                editDob.ifEmpty { "Date of Birth" },
                                fontFamily = Nunito,
                                color = PrimaryTextBrown
                            )
                        }
                        OnboardingField("Email", editEmail) { editEmail = it }
                        GenderDropdown(gender = editGender, onGenderSelected = { editGender = it })

                    } else {
                        InfoCard("Name: $username")
                        InfoCard("Age: $age")
                        InfoCard("Email: $email")
                        InfoCard("Gender: $gender")
                    }

                    if (!isEditing) {
                        InfoCard("Total Notes: ${notes.size} notes")
                        InfoCard("Mood This Week: ${moodThisWeek?.emoji ?: "🙁"} ${moodThisWeek?.label ?: "No notes yet"}")
                        InfoCard(
                            "View all notes",
                            onClick = { navController.navigate(NavRoutes.NotesListScreen) })

                        Button(
                            onClick = {
                                showDeleteConfirm = true
                            },
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

                    if (showDeleteConfirm) {
                        AlertDialog(
                            onDismissRequest = { showDeleteConfirm = false },
                            containerColor = SoftWhite,
                            title = {
                                Text(
                                    "Leave Teddy? 🥲",
                                    fontFamily = LobsterTwo,
                                    fontSize = 28.sp,
                                    color = PrimaryTextBrown
                                )
                            },
                            text = {
                                Text(
                                    "All your notes and memories will be gone forever. Teddy will miss you. 🥺",
                                    fontFamily = Nunito,
                                    fontSize = 16.sp,
                                    color = PrimaryTextBrown
                                )
                            },
                            confirmButton = {
                                TextButton(onClick = {
                                    noteViewModel.deleteAllNotes()
                                    userViewModel.clearUser()
                                    navController.navigate(NavRoutes.HomeScreen) {
                                        popUpTo(NavRoutes.HomeScreen) { inclusive = true }
                                    }
                                }) {
                                    Text("Yes, leave", fontSize = 16.sp,fontFamily = Nunito, color = BearDeep)
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = { showDeleteConfirm = false }) {
                                    Text("Stay 🐻",fontSize = 16.sp, fontFamily = Nunito, color = BearPrimary)
                                }
                            })
                    }

                    Text(
                        "~made with 💌 by Vanshika~",
                        fontFamily = Nunito,
                        fontSize = 12.sp,
                        color = PrimaryTextBrown.copy(alpha = 0.5f),
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(8.dp)
                    )
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
            .padding(16.dp)) {
        Text(text, fontFamily = Nunito, fontSize = 18.sp, color = PrimaryTextBrown)
    }
}