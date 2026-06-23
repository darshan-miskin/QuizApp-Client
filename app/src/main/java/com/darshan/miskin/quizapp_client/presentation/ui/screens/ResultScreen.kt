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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.darshan.miskin.quizapp_client.R
import com.darshan.miskin.quizapp_client.presentation.model.QuizResult

@Composable
fun ResultScreen(quizResult: QuizResult) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            stringResource(R.string.total_questions_n, quizResult.totalQuestions),
            style = MaterialTheme.typography.bodyMedium,
            fontSize = MaterialTheme.typography.bodyLarge.fontSize
        )
        Spacer(modifier = Modifier.size(20.dp))
        Text(
            stringResource(R.string.correct_answers_n, quizResult.correctAnswers),
            style = MaterialTheme.typography.bodyMedium,
            fontSize = MaterialTheme.typography.bodyMedium.fontSize
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            stringResource(R.string.incorrect_answers_n, quizResult.incorrectAnswers),
            style = MaterialTheme.typography.bodyMedium,
            fontSize = MaterialTheme.typography.bodyMedium.fontSize
        )
    }
}

@Preview
@Composable
fun ResultScreenPreview() {
    Surface(modifier = Modifier.fillMaxSize()) {
        ResultScreen(QuizResult( 5, 5))
    }
}