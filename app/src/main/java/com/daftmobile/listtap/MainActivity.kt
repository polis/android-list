package com.daftmobile.listtap

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Remove code below if you would like to code using classic Android layouts instead of Jetpack Compose
        findViewById<ComposeView>(R.id.greeting).setContent {
            MaterialTheme(if (isSystemInDarkTheme()) darkColors() else lightColors()) {
                Content()
            }
        }
    }
}

// Remove code below if you would like to code classic Android layouts instead of Jetpack Compose
@Composable
private fun Content() {
    Text(
        text = "Hello World!!",
        style = MaterialTheme.typography.h5,
        color = MaterialTheme.colors.primary,
        modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .wrapContentWidth(Alignment.CenterHorizontally)
    )
}
