// IQuizCallBackInterface.aidl
package com.darshan.miskin.quizapp_server;

interface IQuizCallBackInterface {
    oneway void onQuizLoaded();
    oneway void onQuizComplete();
    oneway void onError(String errorMessage);
}