package com.myapp.downloadablefonts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.myapp.downloadablefonts.ui.FontListScreen
import com.myapp.downloadablefonts.ui.theme.DownloadableFontsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DownloadableFontsTheme {
                FontListScreen()
            }
        }
    }
}
