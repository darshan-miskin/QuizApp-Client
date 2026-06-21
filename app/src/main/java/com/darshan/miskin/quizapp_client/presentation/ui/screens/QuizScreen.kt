package com.darshan.miskin.quizapp_client.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import com.darshan.miskin.quizapp_client.presentation.ui.theme.PurpleGrey80
import com.darshan.miskin.quizapp_server.QuizData
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

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
                    text = HtmlCompat.fromHtml(quizData.question, HtmlCompat.FROM_HTML_MODE_COMPACT)
                        .toString(),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
            }
        }

        var selectedAnswer by remember { mutableStateOf<String?>(null) }

        LaunchedEffect(selectedAnswer) {
            if (selectedAnswer!=null){
                delay(500.milliseconds)
                selectedAnswer = null
                getNextQuestion()
            }
        }

        shuffledAnswers.forEach {
            val isCorrect = it == quizData.correct_answer
            val isSelected = it == selectedAnswer

            val containerColor = if (isSelected) {if (isCorrect) Color.Green else Color.Red } else PurpleGrey80
            val contentColor = if (isSelected) Color.White else Color.DarkGray

            Button(
                modifier = Modifier.fillMaxWidth(),
                enabled = selectedAnswer == null,
                colors = ButtonDefaults.buttonColors(
                    disabledContainerColor = containerColor,
                    disabledContentColor = contentColor,
                    containerColor = containerColor,
                    contentColor = contentColor,
                ),
                onClick = {
                    selectedAnswer = it
                }) {
                Text(
                    text = HtmlCompat.fromHtml(it, HtmlCompat.FROM_HTML_MODE_COMPACT).toString()
                )
            }
        }
    }
}

@Preview
@Composable
fun QuizScreenPreview() {
    Surface(modifier = Modifier.fillMaxSize()) {
        QuizScreen(QuizData(), arrayListOf()) {}
    }
}