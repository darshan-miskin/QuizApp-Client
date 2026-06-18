package com.darshan.miskin.quizapp_client.presentation.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.School
import androidx.compose.ui.graphics.vector.ImageVector


enum class DESTINATIONS(
    val route: String,
    val label: String,
    val icon: ImageVector,
    val description: String
) {
    QUIZ_SCREEN("quiz_screen", "Quiz", Icons.Default.School, ""),
    RESULT_SCREEN("result_screen", "Result", Icons.Default.BarChart, ""),
}
