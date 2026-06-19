// IQuizCallBackInterface.aidl
package com.darshan.miskin.quizapp_server;

interface IQuizCallBackInterface {
    void onQuizLoaded();
    void onQuizComplete(boolean isComplete);
}