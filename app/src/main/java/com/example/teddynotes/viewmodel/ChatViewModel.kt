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
    private val _messages = MutableStateFlow<List<Message>>(
        listOf(
            Message(
                "Hey! I'm Teddy 🐻 How are you feeling today?",
                MessageType.TEDDY
            )
        )
    )

    val messages = _messages.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun sendMessage(text: String, notes: List<Note>) {
        Log.d("TEST_FLOW", "sendMessage: $text")
        _messages.value = _messages.value + Message(text, MessageType.USER)
        _isLoading.value = true

        viewModelScope.launch {
            try {
                val response = repository.getReply(text,notes)
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
