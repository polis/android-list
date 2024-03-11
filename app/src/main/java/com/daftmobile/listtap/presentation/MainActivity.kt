package com.daftmobile.listtap.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.platform.ComposeView
import com.daftmobile.listtap.R
import com.daftmobile.listtap.presentation.maincontent.MainContent

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Remove code below if you would like to code using classic Android layouts instead of Jetpack Compose
        findViewById<ComposeView>(R.id.greeting).setContent {
            MaterialTheme(if (isSystemInDarkTheme()) darkColors() else lightColors()) {
                MainContent()
            }
        }
    }
}