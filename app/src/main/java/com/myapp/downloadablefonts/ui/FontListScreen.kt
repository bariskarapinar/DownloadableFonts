package com.myapp.downloadablefonts.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.myapp.downloadablefonts.FontViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FontListScreen(viewModel: FontViewModel = viewModel()) {
    val fontFamily by viewModel.fontFamily
    val isLoading by viewModel.isLoading
    val error by viewModel.error

    val fonts = listOf(
        "Lobster Two",
        "Pacifico",
        "Dancing Script",
        "Bangers",
        "Montserrat",
        "Open Sans",
        "Roboto",
        "Playfair Display",
        "Indie Flower",
        "Shadows Into Light",
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Downloadable Fonts") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            // Preview Section
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    if (isLoading) {
                        CircularProgressIndicator()
                    } else if (error != null) {
                        Text(text = error!!, color = Color.Red, modifier = Modifier.padding(16.dp))
                    } else {
                        Text(
                            text = "The quick brown fox jumps over the lazy dog",
                            style = LocalTextStyle.current.copy(
                                fontFamily = fontFamily,
                                fontSize = 24.sp
                            ),
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }

            Text(
                text = "Select a font to preview:",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(fonts) { font ->
                    ListItem(
                        headlineContent = { Text(font) },
                        modifier = Modifier
                            .clickable { viewModel.fetchFont(font) }
                            .padding(horizontal = 8.dp),
                        supportingContent = { Text("Click to download and apply") },
                        trailingContent = {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null
                            )
                        }
                    )
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                }
            }
        }
    }
}
