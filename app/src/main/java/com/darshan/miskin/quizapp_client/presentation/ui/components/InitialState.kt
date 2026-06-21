package com.darshan.miskin.quizapp_client.presentation.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.darshan.miskin.quizapp_client.R

@Composable
fun InitialState(startQuiz: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = startQuiz) {
            Text(text = stringResource(R.string.start_quiz))
        }
    }
}

@Preview
@Composable
fun InitialStatePreview(){
    Surface(modifier = Modifier.fillMaxSize()) {
        InitialState(){}
    }
}
