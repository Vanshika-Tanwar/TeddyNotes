package com.example.teddynotes.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.teddynotes.ui.theme.BearDeep
import com.example.teddynotes.ui.theme.LobsterTwo
import com.example.teddynotes.ui.theme.NoteBeige
import com.example.teddynotes.ui.theme.Nunito
import com.example.teddynotes.ui.theme.PrimaryTextBrown
import com.example.teddynotes.ui.theme.SoftWhite

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun PreviewDialog() {
//    Box(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//        OnboardingDialog(onComplete = { _, _, _, _ -> })
//    }
//}

@Composable
fun OnboardingDialog(onComplete: (String, String, String, String) -> Unit) {
    var username by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }

    Dialog(onDismissRequest = {}) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(24.dp))
                .background(SoftWhite)
                .padding(24.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Helloo! 🐻", fontFamily = LobsterTwo, fontSize = 28.sp, color = PrimaryTextBrown
                )
                Text(
                    "Tell me a little about yourself",
                    fontFamily = Nunito,
                    fontSize = 14.sp,
                    color = PrimaryTextBrown.copy(alpha = 0.6f)
                )
                Spacer(Modifier.height(8.dp))
                OnboardingField("Name", username) { username = it }

                var showDatePicker by remember { mutableStateOf(false) }
                if (showDatePicker) {
                    val datePickerState = rememberDatePickerState(
                        initialSelectedDateMillis = java.time.LocalDate.of(2000, 1, 1)
                            .atStartOfDay(java.time.ZoneId.of("UTC")).toInstant()
                            .toEpochMilli()
                    )
                    DatePickerDialog(
                        onDismissRequest = { showDatePicker = false },
                        confirmButton = {
                            TextButton(onClick = {
                                datePickerState.selectedDateMillis?.let { millis ->
                                    val localDate = java.time.Instant.ofEpochMilli(millis)
                                        .atZone(java.time.ZoneId.systemDefault()).toLocalDate()
                                    dob = localDate.format(
                                        java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")
                                    )
                                }
                                showDatePicker = false
                            }) { Text("OK") }
                        },
                        dismissButton = {
                            TextButton(onClick = { showDatePicker = false }) { Text("Cancel") }
                        }) {
                        DatePicker(state = datePickerState)
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(NoteBeige)
                        .clickable { showDatePicker = true }
                        .padding(horizontal = 16.dp, vertical = 12.dp)) {
                    Text(
                        text = dob.ifEmpty { "Date of Birth" },
                        fontFamily = Nunito,
                        fontSize = 15.sp,
                        color = if (dob.isEmpty()) PrimaryTextBrown.copy(alpha = 0.4f) else PrimaryTextBrown
                    )
                }

                OnboardingField("Email", email) { email = it }
                GenderDropdown(gender = gender, onGenderSelected = { gender = it })
                val allFilled =
                    username.isNotBlank() && dob.length == 10 && email.isNotBlank() && gender.isNotBlank()
                Button(
                    onClick = {
                        if (allFilled) onComplete(username, dob, email, gender)
                    }, enabled = allFilled, colors = ButtonDefaults.buttonColors(
                        containerColor = BearDeep,
                        disabledContainerColor = BearDeep.copy(alpha = 0.4f)
                    ), shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Let's go! 🐾", fontFamily = Nunito, color = NoteBeige, fontSize = 16.sp)
                }
            }
        }

    }
}

@Composable
fun OnboardingField(label: String, value: String, onValueChange: (String) -> Unit) {
    BasicTextField(
        value = value, onValueChange = onValueChange, textStyle = TextStyle(
            fontFamily = Nunito, fontSize = 15.sp, color = PrimaryTextBrown
        ), decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(NoteBeige)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                if (value.isEmpty()) {
                    Text(
                        label,
                        fontFamily = Nunito,
                        fontSize = 15.sp,
                        color = PrimaryTextBrown.copy(alpha = 0.4f)
                    )
                }
                innerTextField()
            }

        }, modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun GenderDropdown(gender: String, onGenderSelected: (String) -> Unit) {
    val genderOptions = listOf("Male", "Female", "Non-binary", "Prefer not to say")
    var expanded by remember { mutableStateOf(false) }

    Box {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(NoteBeige)
                .clickable { expanded = true }
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(
                text = gender.ifEmpty { "Gender" },
                fontFamily = Nunito,
                fontSize = 15.sp,
                color = if (gender.isEmpty()) PrimaryTextBrown.copy(alpha = 0.4f) else PrimaryTextBrown
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(NoteBeige)
        ) {
            genderOptions.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option, fontFamily = Nunito, color = PrimaryTextBrown) },
                    onClick = {
                        onGenderSelected(option)
                        expanded = false
                    },
                    modifier = Modifier.background(NoteBeige)
                )
            }
        }
    }
}