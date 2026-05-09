package com.example.teddynotes.ui.chatbot

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.teddynotes.R
import com.example.teddynotes.model.Message
import com.example.teddynotes.model.MessageType
import com.example.teddynotes.ui.common.TeddyTopBar
import com.example.teddynotes.ui.theme.BackgroundGreen
import com.example.teddynotes.ui.theme.BearDeep
import com.example.teddynotes.ui.theme.BearPrimary
import com.example.teddynotes.ui.theme.Nunito
import com.example.teddynotes.ui.theme.PrimaryTextBrown
import com.example.teddynotes.ui.theme.SoftWhite

@Composable
fun ChatBotScreen(navController: NavController) {
    val messages = listOf(
        Message("Hey! How are you feeling today?", MessageType.TEDDY),
        Message("I'm okay I guess", MessageType.USER),
        Message("Tell me more, I'm here for you 🐻", MessageType.TEDDY)
    )
    var input by remember { mutableStateOf("") }
    Scaffold(containerColor = BackgroundGreen) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            TeddyTopBar(onBack = { navController.popBackStack() })
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(messages) { message ->
                    ChatBubble(message = message)
                }
            }

            //input bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(SoftWhite)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = input,
                    onValueChange = { input = it },
                    textStyle = TextStyle(
                        fontFamily = Nunito,
                        fontSize = 14.sp,
                        color = BearDeep
                    ),
                    modifier = Modifier.weight(1f),
                    decorationBox = { innerTextField ->
                        if (input.isEmpty()) Text(
                            "Talk to Teddy :]",
                            color = BearDeep.copy(alpha = 0.4f),
                            fontFamily = Nunito
                        )
                        innerTextField()
                    }
                )

                IconButton(onClick = { }) {
                    Icon(
                        Icons.Default.Send,
                        contentDescription = "Send", tint = BearPrimary
                    )
                }
            }
        }
    }

}

@Composable
fun ChatBubble(message: Message) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (message.type == MessageType.TEDDY) Arrangement.Start
        else Arrangement.End,
        verticalAlignment = Alignment.Top
    ) {
        if (message.type == MessageType.TEDDY) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(SoftWhite.copy(alpha = 0.65f))
                    .padding(6.dp)
                ,
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.bear_logo),
                    contentDescription = "Teddy",
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
            Spacer(Modifier.width(8.dp))
        }
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(SoftWhite.copy(alpha = 0.65f))
                .padding(12.dp)
        ) {
            Text(
                text = message.text,
                fontFamily = Nunito,
                fontSize = 14.sp,
                color = PrimaryTextBrown
            )
        }
    }
}