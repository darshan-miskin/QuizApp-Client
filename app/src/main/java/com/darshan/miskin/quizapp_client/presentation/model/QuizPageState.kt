package com.darshan.miskin.quizapp_client.presentation.model

import com.darshan.miskin.quizapp_server.QuizData

sealed interface QuizPageState {
    object Initial : QuizPageState
    object Loading : QuizPageState
    object Error : QuizPageState
    data class Success(val quizData: QuizData): QuizPageState
}