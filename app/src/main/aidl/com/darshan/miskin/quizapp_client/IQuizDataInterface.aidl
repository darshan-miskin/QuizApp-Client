// IQuizDataInterface.aidl
package com.darshan.miskin.quizapp_client;

import com.darshan.miskin.quizapp_client.QuizData;
import com.darshan.miskin.quizapp_client.IQuizCompleteInterface;

interface IQuizDataInterface {
    QuizData getNextQuestion();

    void registerQuizCallback(IQuizCompleteInterface iQuizCompleteInterface);
    void unregisterQuizCallback(IQuizCompleteInterface iQuizCompleteInterface);
}