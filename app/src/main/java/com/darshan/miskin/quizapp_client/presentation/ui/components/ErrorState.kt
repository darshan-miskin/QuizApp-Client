package com.darshan.miskin.quizapp_client.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.darshan.miskin.quizapp_client.R

@Composable
fun ErrorState(startQuiz: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(stringResource(R.string.oops_something_went_wrong), textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.padding(16.dp))
        Button(onClick = startQuiz) {
            Text(text = stringResource(R.string.start_quiz))
        }
    }
}

@Preview
@Composable
fun ErrorStatePreview(){
    Surface(modifier = Modifier.fillMaxSize()) {
        ErrorState(){}
    }
}
