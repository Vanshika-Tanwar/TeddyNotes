package com.example.teddynotes.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class NavRoutes {

    @Serializable
    object SplashScreen : NavRoutes()

    @Serializable
    object HomeScreen : NavRoutes()

    @Serializable
    data class NoteScreen(val date: String = "") : NavRoutes()

    @Serializable
    object ChatBotScreen : NavRoutes()

    @Serializable
    object NotesListScreen : NavRoutes()

    @Serializable
    object ProfileScreen : NavRoutes()
}