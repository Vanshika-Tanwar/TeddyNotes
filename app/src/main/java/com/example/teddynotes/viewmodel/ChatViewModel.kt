package com.example.teddynotes.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teddynotes.model.Message
import com.example.teddynotes.model.MessageType
import com.example.teddynotes.model.Note
import com.example.teddynotes.repository.ChatRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChatViewModel(
    private val repository: ChatRepository
) : ViewModel()  {

    private val greetings = listOf(
        "Hey! I'm Teddy 🐻 How are you feeling today?",
        "Hi there! 🐻 What's on your mind?",
        "Hey you! 🐻 How's your heart doing today?",
        "Hello! 🐻 What's on your mind?",
        "Welcome back! 🐻 How are you holding up?",
        "Glad to see you! How are things going?"
    )
    private val _messages = MutableStateFlow<List<Message>>(
        listOf(
            Message(
                greetings.random(),
                MessageType.TEDDY
            )
        )
    )

    val messages = _messages.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun sendMessage(text: String, notes: List<Note>, username: String, age: String, gender: String) {
        _messages.value = _messages.value + Message(text, MessageType.USER)
        _isLoading.value = true

        viewModelScope.launch {
            try {
                val response = repository.getReply(text, notes, username, age, gender)
                _messages.value = _messages.value + Message(response, MessageType.TEDDY)
            } catch (e: Exception){
                _messages.value = _messages.value + Message(
                    "Teddy is taking a nap🐻💤", MessageType.TEDDY
                )
            }finally {
                _isLoading.value = false
            }
        }
    }
}
