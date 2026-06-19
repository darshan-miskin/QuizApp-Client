package com.darshan.miskin.quizapp_client.presentation.ui.screens

import androidx.lifecycle.ViewModel
import com.darshan.miskin.quizapp_client.presentation.model.QuizPageState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {
    private val _quizPageState = MutableStateFlow<QuizPageState>(QuizPageState.Initial)
    val quizPageState : StateFlow<QuizPageState>
        get() = _quizPageState.asStateFlow()

    fun setQuizPageState(quizPageState: QuizPageState){
        _quizPageState.value = quizPageState
    }
}