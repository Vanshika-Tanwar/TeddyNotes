package com.example.teddynotes.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.teddynotes.chatbot.ChatBotScreen
import com.example.teddynotes.notes_list.NotesListScreen
import com.example.teddynotes.profile.ProfileScreen
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

        composable<NavRoutes.NotesList>{
            NotesListScreen(navController)
        }

        composable<NavRoutes.ProfileScreen>{
            ProfileScreen(navController)
        }



    }
}