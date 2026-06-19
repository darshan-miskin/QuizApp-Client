// IQuizDataInterface.aidl
package com.darshan.miskin.quizapp_server;

import com.darshan.miskin.quizapp_server.QuizData;
import com.darshan.miskin.quizapp_server.IQuizCallBackInterface;

interface IQuizDataInterface {
    QuizData getNextQuestion();

    void registerQuizCallback(IQuizCallBackInterface iQuizCallBackInterface);
    void unregisterQuizCallback(IQuizCallBackInterface iQuizCallBackInterface);
}