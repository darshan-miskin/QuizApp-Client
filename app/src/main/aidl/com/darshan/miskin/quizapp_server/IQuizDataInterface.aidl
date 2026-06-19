// IQuizDataInterface.aidl
package com.darshan.miskin.quizapp_server;

import com.darshan.miskin.quizapp_server.QuizData;
import com.darshan.miskin.quizapp_server.IQuizCompleteInterface;

interface IQuizDataInterface {
    QuizData getNextQuestion();

    void registerQuizCallback(IQuizCompleteInterface iQuizCompleteInterface);
    void unregisterQuizCallback(IQuizCompleteInterface iQuizCompleteInterface);
}