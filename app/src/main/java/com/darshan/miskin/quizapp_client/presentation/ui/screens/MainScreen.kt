package com.darshan.miskin.quizapp_client.presentation.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.darshan.miskin.quizapp_client.presentation.model.DESTINATIONS
import com.darshan.miskin.quizapp_client.presentation.model.QuizPageState
import com.darshan.miskin.quizapp_client.presentation.ui.components.LoadingState
import com.darshan.miskin.quizapp_client.presentation.ui.theme.QuizApp_ClientTheme

@Composable
fun MainScreen(quizPageState: QuizPageState, getNextQuestion: () -> Unit, startQuiz: () -> Unit) {
    val navController = rememberNavController()
    var selectedDestination by rememberSaveable { mutableStateOf(DESTINATIONS.QUIZ_SCREEN.route) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                DESTINATIONS.entries.forEach {
                    NavigationBarItem(
                        selected = selectedDestination == it.route,
                        onClick = {
                            navController.navigate(route = it.route)
                            selectedDestination = it.route
                        },
                        icon = {
                            Icon(it.icon, contentDescription = it.description)
                        },
                        label = {
                            Text(
                                text = it.label
                            )
                        },
                        alwaysShowLabel = true,
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(modifier = Modifier.padding(innerPadding), navController = navController, startDestination = DESTINATIONS.QUIZ_SCREEN.route) {
            composable(DESTINATIONS.QUIZ_SCREEN.route) {
                when(quizPageState){
                    QuizPageState.Error -> Text("Something Went Wrong!")
                    QuizPageState.Initial -> InitialState(startQuiz)
                    QuizPageState.Loading -> LoadingState()
                    is QuizPageState.Success -> QuizScreen(quizPageState.quizData, quizPageState.shuffledAnswers, getNextQuestion)
                }
            }
            composable(DESTINATIONS.RESULT_SCREEN.route) { ResultScreen() }
        }
    }
}

@Composable
fun InitialState(startQuiz: () -> Unit){
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center){
        Button(onClick = startQuiz) {
            Text(text = "Start Quiz")

        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    QuizApp_ClientTheme {
        MainScreen(QuizPageState.Initial,{}){}
    }
}