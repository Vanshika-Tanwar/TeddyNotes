package com.example.teddynotes.ui.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.teddynotes.model.Mood
import com.example.teddynotes.model.Note
import com.example.teddynotes.ui.common.TeddyTopBar
import com.example.teddynotes.ui.theme.BackgroundGreen
import com.example.teddynotes.ui.theme.BearDeep
import com.example.teddynotes.ui.theme.NoteBeige
import com.example.teddynotes.ui.theme.Nunito
import com.example.teddynotes.ui.theme.PrimaryTextBrown
import com.example.teddynotes.viewmodel.NoteViewModel

@Composable
fun NoteScreen(navController: NavController, noteViewModel: NoteViewModel, noteDate: String = "") {
    var showDialog by remember { mutableStateOf(noteDate.isEmpty()) }
    var selectedMood by remember { mutableStateOf<Mood?>(null) }
    var title by remember { mutableStateOf("") }
    val date = remember {
        java.time.LocalDate.now()
            .format(java.time.format.DateTimeFormatter.ofPattern("dd MMM yyyy"))
    }
    var content by remember { mutableStateOf("") }
    val isToday = date == noteDate || noteDate.isEmpty()

    LaunchedEffect(noteDate) {
        if (noteDate.isNotEmpty()) {
            val note = noteViewModel.getNoteByDate(noteDate)
            note?.let {
                title = it.title
                content = it.content
                selectedMood = it.mood
                showDialog = false
            }
        }
    }

    if (showDialog) {
        MoodPickerDialog(onMoodSelected = { mood ->
            selectedMood = mood
            showDialog = false
        }, onDismissRequest = {
            if (selectedMood != null) {
                showDialog = false
            } else {
                navController.popBackStack()
            }
        }
        )
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
                    .background(NoteBeige)
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        //title+date
                        BasicTextField(
                            value = title,
                            onValueChange = { if (isToday) title = it },
                            textStyle = TextStyle(
                                fontSize = 24.sp,
                                color = PrimaryTextBrown,
                                fontFamily = Nunito
                            ),
                            decorationBox = { innerTextField ->
                                if (title.isEmpty()) {
                                    Text(
                                        "TITLE",
                                        fontFamily = Nunito,
                                        fontSize = 24.sp,
                                        color = PrimaryTextBrown.copy(alpha = 0.5f)
                                    )
                                }
                                innerTextField()
                            },
                            modifier = Modifier.weight(1f)

                        )

                        Text(
                            text = noteDate.ifEmpty { date },
                            fontFamily = Nunito,
                            fontSize = 14.sp,
                            color = PrimaryTextBrown
                        )

                    }
                    HorizontalDivider(color = BearDeep)
                    Spacer(Modifier.height(8.dp))
                    TextField(
                        value = content,
                        onValueChange = { if (isToday) content = it },
                        placeholder = {
                            Text(
                                "Write your thoughts here...",
                                fontFamily = Nunito,
                                color = PrimaryTextBrown.copy(alpha = 0.5f)
                            )
                        },
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            color = PrimaryTextBrown,
                            fontFamily = Nunito
                        ),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        //emoji+save button
                        Text(
                            text = selectedMood?.emoji ?: "🟡",
                            fontSize = 36.sp,
                            modifier = if (isToday) Modifier.clickable {
                                showDialog = true
                            } else Modifier
                        )
                        if (isToday) {
                            Button(
                                onClick = {
                                    //save logic
//                                if(title.isNotBlank() && selectedMood!=null){
//                                    noteViewModel.addNote(
//                                        Note(
//                                            title = title,
//                                            content = content,
//                                            date = date,
//                                            mood = selectedMood!!
//                                        )
//                                    )
//                                } but better use let ->
                                    selectedMood?.let { mood ->
                                        if (title.isNotBlank()) {
                                            noteViewModel.addNote(
                                                Note(
                                                    title = title,
                                                    content = content,
                                                    date = date,
                                                    mood = mood
                                                )
                                            )
                                            navController.popBackStack()
                                        }
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = BearDeep),
                                shape = RoundedCornerShape(24.dp)
                            ) {
                                Text(
                                    "SAVE",
                                    fontFamily = Nunito,
                                    fontWeight = FontWeight.Medium,
                                    color = NoteBeige,
                                    fontSize = 20.sp
                                )
                            }
                        }
                    }
                }

            }
        }
    }
}