package com.example.teddynotes.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.teddynotes.ui.chatbot.ChatBotScreen
import com.example.teddynotes.ui.notes_list.NotesListScreen
import com.example.teddynotes.ui.profile.ProfileScreen
import com.example.teddynotes.ui.home.HomeScreen
import com.example.teddynotes.ui.notes.NoteScreen
import com.example.teddynotes.ui.splash.SplashScreen

@Composable
fun NavGraph(){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRoutes.SplashScreen
    ){
        composable<NavRoutes.SplashScreen>{
            SplashScreen(navController)
        }

        composable<NavRoutes.HomeScreen>{
            HomeScreen(navController)
        }

        composable<NavRoutes.NoteScreen>{
            NoteScreen(navController)
        }

        composable<NavRoutes.ChatBotScreen>{
            ChatBotScreen(navController)
        }

        composable<NavRoutes.NotesListScreen>{
            NotesListScreen(navController)
        }

        composable<NavRoutes.ProfileScreen>{
            ProfileScreen(navController)
        }



    }
}