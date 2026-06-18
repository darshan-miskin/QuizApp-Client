package com.darshan.miskin.quizapp_client.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.darshan.miskin.quizapp_client.data.QuizData

@Composable
fun QuizScreen(quizData: QuizData) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = quizData.difficulty,
                    style = MaterialTheme.typography.labelMedium
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = quizData.question,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
            val answers = quizData.incorrect_answers.plus(quizData.correct_answer)
            LaunchedEffect(answers) {
                answers.shuffled()
            }
            answers.forEach {
                Button(modifier = Modifier.fillMaxWidth(), onClick = {}) { Text(it) }
            }
        }
    }
}

@Preview
@Composable
fun QuizScreenPreview() {
    QuizScreen(QuizData("a", "d", "Hard", listOf("a", "b", "c"), "a long Question goes here?", ""))
}