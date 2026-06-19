// QuizData.aidl
package com.darshan.miskin.quizapp_client;

parcelable QuizData{
    @JavaPassthrough(annotation="@androidx.annotation.NonNull") String category;
    @JavaPassthrough(annotation="@androidx.annotation.NonNull") String correct_answer;
    @JavaPassthrough(annotation="@androidx.annotation.NonNull") String difficulty;
    @JavaPassthrough(annotation="@androidx.annotation.NonNull") List<String> incorrect_answers;
    @JavaPassthrough(annotation="@androidx.annotation.NonNull") String question;
    @JavaPassthrough(annotation="@androidx.annotation.NonNull") String type;
}