package com.example.teddynotes.model

enum class MessageType{TEDDY, USER}

data class Message (
    val text: String,
    val type :  MessageType
)