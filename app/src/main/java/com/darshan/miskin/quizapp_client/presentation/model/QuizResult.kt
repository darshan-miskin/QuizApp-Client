package com.darshan.miskin.quizapp_client.presentation.model

data class QuizResult(var correctAnswers: Int, var incorrectAnswers: Int){
    val totalQuestions: Int
        get() = correctAnswers + incorrectAnswers
}