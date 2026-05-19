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
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen().setKeepOnScreenCondition { false }
        val analytics = FirebaseAnalytics.getInstance(this)
        analytics.logEvent("app_opened", null)
        setContent {
            TeddyNotesTheme {
                NavGraph()
            }
        }
    }
}
