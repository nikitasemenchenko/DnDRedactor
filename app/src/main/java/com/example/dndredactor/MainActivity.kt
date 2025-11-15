package com.example.dndredactor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.dndredactor.ui.DndRedactorApp
import com.example.dndredactor.ui.theme.BackPurple
import com.example.dndredactor.ui.theme.DnDRedactorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DnDRedactorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = BackPurple
                ) {
                    DndRedactorApp()
                }
            }
        }
    }
}