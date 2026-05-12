package com.example.teddynotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.teddynotes.navigation.NavGraph
import com.example.teddynotes.ui.theme.TeddyNotesTheme
import com.example.teddynotes.viewmodel.NoteViewModel
import com.example.teddynotes.viewmodel.NoteViewModelFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen().setKeepOnScreenCondition { false }
        setContent {
            TeddyNotesTheme {
                NavGraph()
            }
        }
    }
}
