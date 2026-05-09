package com.example.teddynotes.ui.notes

import android.R
import androidx.compose.foundation.background
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
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.teddynotes.model.Mood
import com.example.teddynotes.ui.theme.BackgroundGreen
import com.example.teddynotes.ui.theme.BearDeep
import com.example.teddynotes.ui.theme.NoteBeige
import com.example.teddynotes.ui.theme.Nunito
import com.example.teddynotes.ui.theme.PrimaryTextBrown

@Composable
fun NoteScreen(navController: NavController) {
    var showDialog by remember { mutableStateOf(true) }
    var selectedMood by remember { mutableStateOf<Mood?>(null) }
    var title by remember { mutableStateOf("") }
    val date = remember {
        java.time.LocalDate.now()
            .format(java.time.format.DateTimeFormatter.ofPattern("dd MMM yyyy"))
    }
    var content by remember { mutableStateOf("") }
    if (showDialog) {
        MoodPickerDialog(onMoodSelected = { mood ->
            selectedMood = mood
            showDialog = false
        }, onDismissRequest = {navController.popBackStack() }
        )
    }
    Scaffold(containerColor = BackgroundGreen) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .padding(20.dp)
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
                        onValueChange = { title = it },
                        textStyle = TextStyle(
                            fontSize = 24.sp,
                            color = PrimaryTextBrown,
                            fontFamily = Nunito
                        ),
                        decorationBox = {
                            innerTextField ->
                            if(title.isEmpty()){
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
                        text = date, fontFamily = Nunito,
                        fontSize = 14.sp,
                        color = PrimaryTextBrown
                    )

                }
                HorizontalDivider(color = BearDeep)
                Spacer(Modifier.height(8.dp))
                TextField(
                    value = content,
                    onValueChange = {content = it},
                    placeholder = { Text("Write your thoughts here...", fontFamily = Nunito, color = PrimaryTextBrown.copy(alpha = 0.5f))},
                    textStyle = TextStyle(fontSize = 16.sp, color = PrimaryTextBrown, fontFamily = Nunito),
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
                        fontSize = 36.sp
                    )
                    Button(
                        onClick = {
                        /* save this note haha, for now lets navigate back to HomeScreen*/
                            navController.popBackStack()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = BearDeep),
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Text("SAVE", fontFamily = Nunito, fontWeight = FontWeight.Medium, color = NoteBeige, fontSize = 20.sp)
                    }
                }
            }

        }

    }
}