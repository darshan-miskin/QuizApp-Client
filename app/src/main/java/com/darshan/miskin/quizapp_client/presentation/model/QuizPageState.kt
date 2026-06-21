package com.darshan.miskin.quizapp_client.presentation.model

import androidx.core.text.HtmlCompat
import com.darshan.miskin.quizapp_server.QuizData

sealed interface QuizPageState {
    object Initial : QuizPageState
    object Loading : QuizPageState
    object Error : QuizPageState
    object Waiting : QuizPageState
    data class Success(val quizData: QuizData) : QuizPageState {
        val shuffledAnswers = quizData.incorrect_answers.plus(quizData.correct_answer).shuffled()
            .map { HtmlCompat.fromHtml(it, HtmlCompat.FROM_HTML_MODE_COMPACT).toString() }
    }
}