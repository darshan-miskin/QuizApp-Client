package com.darshan.miskin.quizapp_client.presentation.ui.screens

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.darshan.miskin.quizapp_client.QuizData

@Composable
fun QuizScreen(quizData: QuizData, getNextQuestion: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        val context = LocalContext.current
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
            Button(modifier = Modifier.fillMaxWidth(), onClick = {
                if (it == quizData.correct_answer) {
                    getNextQuestion()
                    Toast.makeText(context, "Correct Answer!", Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(context, "Wrong Answer!", Toast.LENGTH_SHORT).show()
                }
            }) { Text(it) }
        }
    }
}

@Preview
@Composable
fun QuizScreenPreview() {
    Surface(modifier = Modifier.fillMaxSize()) {
        QuizScreen(QuizData()){}
    }
}