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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import com.darshan.miskin.quizapp_server.QuizData

@Composable
fun QuizScreen(quizData: QuizData, shuffledAnswers: List<String>, getNextQuestion: () -> Unit) {
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
                    text = HtmlCompat.fromHtml(quizData.question, HtmlCompat.FROM_HTML_MODE_COMPACT).toString(),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
        shuffledAnswers.forEach {
            Button(modifier = Modifier.fillMaxWidth(), onClick = {
                if (it == HtmlCompat.fromHtml(quizData.correct_answer, HtmlCompat.FROM_HTML_MODE_COMPACT).toString()) {
                    getNextQuestion()
                }
            }) { Text(HtmlCompat.fromHtml(it, HtmlCompat.FROM_HTML_MODE_COMPACT).toString()) }
        }
    }
}

@Preview
@Composable
fun QuizScreenPreview() {
    Surface(modifier = Modifier.fillMaxSize()) {
        QuizScreen(QuizData(), arrayListOf()){}
    }
}