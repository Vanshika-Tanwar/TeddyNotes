package com.example.teddynotes.navigation

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.teddynotes.ui.chatbot.ChatBotScreen
import com.example.teddynotes.ui.notes_list.NotesListScreen
import com.example.teddynotes.ui.profile.ProfileScreen
import com.example.teddynotes.ui.home.HomeScreen
import com.example.teddynotes.ui.notes.NoteScreen
import com.example.teddynotes.ui.splash.SplashScreen
import com.example.teddynotes.viewmodel.NoteViewModel
import com.example.teddynotes.viewmodel.NoteViewModelFactory

@Composable
fun NavGraph(){
    val navController = rememberNavController()
    val context = LocalContext.current
    val noteViewModel : NoteViewModel = viewModel(
        factory = NoteViewModelFactory(context.applicationContext as Application)
    )

    NavHost(
        navController = navController,
        startDestination = NavRoutes.SplashScreen
    ){
        composable<NavRoutes.SplashScreen>{
            SplashScreen(navController)
        }

        composable<NavRoutes.HomeScreen>{
            HomeScreen(navController, noteViewModel)
        }

        composable<NavRoutes.NoteScreen>{
            NoteScreen(navController, noteViewModel)
        }

        composable<NavRoutes.ChatBotScreen>{
            ChatBotScreen(navController)
        }

        composable<NavRoutes.NotesListScreen>{
            NotesListScreen(navController, noteViewModel)
        }

        composable<NavRoutes.ProfileScreen>{
            ProfileScreen(navController, noteViewModel)
        }



    }
}