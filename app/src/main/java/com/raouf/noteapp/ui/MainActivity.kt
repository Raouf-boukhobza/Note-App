package com.raouf.noteapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.raouf.noteapp.ViewModel.homeScreen.NoteViewModel
import com.raouf.noteapp.ui.theme.NoteAppTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            NoteAppTheme {
                val navController = rememberNavController()
                MyNavHost(navController = navController)
            }
        }
    }
}

