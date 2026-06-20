// IQuizDataInterface.aidl
package com.darshan.miskin.quizapp_server;

import com.darshan.miskin.quizapp_server.QuizData;
import com.darshan.miskin.quizapp_server.IQuizCallBackInterface;

interface IQuizDataInterface {
    QuizData getNextQuestion();
    oneway void refresh();

    oneway void registerQuizCallback(IQuizCallBackInterface iQuizCallBackInterface);
    oneway void unregisterQuizCallback(IQuizCallBackInterface iQuizCallBackInterface);
}