package com.darshan.miskin.quizapp_client.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ResultScreen() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Total Questions: 10", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.size(20.dp))
            Text("Correct Answers: 7", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.size(4.dp))
            Text("Incorrect Answers: 3", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Preview
@Composable
fun ResultScreenPreview() {
    ResultScreen()
}