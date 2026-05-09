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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.teddynotes.model.Mood
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
//        MoodPickerDialog(onMoodSelected = {}, onDismissRequest = {})
//    }
//}


@Composable
fun MoodPickerDialog(onMoodSelected: (Mood) -> Unit, onDismissRequest: () -> Unit) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(24.dp))
                .background(SoftWhite)
                .padding(24.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "How are you?",
                        fontFamily = Nunito,
                        fontWeight = FontWeight.Medium,
                        fontSize = 32.sp,
                        color = PrimaryTextBrown
                    )
                    IconButton(onClick = onDismissRequest) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = PrimaryTextBrown
                        )
                    }
                }
                Spacer(Modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    MoodItem(Mood.HAPPY, onMoodSelected)
                    MoodItem(Mood.SAD, onMoodSelected)
                    MoodItem(Mood.ANGRY, onMoodSelected)
                }
                Spacer(Modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    MoodItem(Mood.CALM, onMoodSelected)
                    MoodItem(Mood.ANXIOUS, onMoodSelected)
                }

            }

        }

    }

}

@Composable
fun MoodItem(mood: Mood, onSelected: (Mood) -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onSelected(mood) }
    ) {
        Text(mood.emoji, fontSize = 48.sp)
        Text(mood.label, fontFamily = Nunito, fontSize = 18.sp, color = PrimaryTextBrown)
    }

}
