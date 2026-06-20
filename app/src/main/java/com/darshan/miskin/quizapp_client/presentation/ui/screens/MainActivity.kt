package com.darshan.miskin.quizapp_client.presentation.ui.screens

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.darshan.miskin.quizapp_client.contract.QuizContract
import com.darshan.miskin.quizapp_server.IQuizDataInterface
import com.darshan.miskin.quizapp_client.presentation.model.QuizPageState
import com.darshan.miskin.quizapp_client.presentation.ui.theme.QuizApp_ClientTheme
import com.darshan.miskin.quizapp_server.IQuizCallBackInterface

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            QuizApp_ClientTheme {
                val quizPageState by viewModel.quizPageState.collectAsStateWithLifecycle()
                MainScreen(
                    quizPageState = quizPageState,
                    getNextQuestion = {
                        nextQuestion()
                    }
                ) {
                    startQuiz()
                }
            }
        }
    }
    fun nextQuestion(){
        iQuizService.nextQuestion?.let {
            viewModel.setQuizPageState(QuizPageState.Success(it))
        }
    }
    fun startQuiz(){
        if(!::iQuizService.isInitialized){
            try {
                val isServerInstalled = this@MainActivity.packageManager.getPackageInfo(
                    QuizContract.SERVER_PACKAGE_NAME,
                    0
                )
                if (isServerInstalled != null) {
                    viewModel.setQuizPageState(QuizPageState.Loading)
                    val intent = Intent(QuizContract.ACTION_START_QUIZ).apply {
                        setPackage(QuizContract.SERVER_PACKAGE_NAME)
                    }
                    bindService(intent, connection, BIND_AUTO_CREATE)
                }
            } catch (e: PackageManager.NameNotFoundException) {
                Toast.makeText(
                    this@MainActivity,
                    "Quiz Server App Not Installed!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }else{
            viewModel.setQuizPageState(QuizPageState.Loading)
            iQuizService.refresh()
        }
    }

    lateinit var iQuizService: IQuizDataInterface
    val iQuizCallBackInterface = object : IQuizCallBackInterface.Stub() {
        override fun onQuizLoaded() {
            nextQuestion()
        }

        override fun onQuizComplete() {
            viewModel.setQuizPageState(QuizPageState.Initial)
        }

        override fun onError(errorMessage: String?) {
            viewModel.setQuizPageState(QuizPageState.Error)
        }

    }

    val connection = object : ServiceConnection {
        override fun onServiceConnected(
            name: ComponentName?,
            service: IBinder?
        ) {
            iQuizService = IQuizDataInterface.Stub.asInterface(service)
            iQuizService.registerQuizCallback(iQuizCallBackInterface)
            iQuizService.refresh()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            unbindService(this)
        }

    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    QuizApp_ClientTheme {
        MainScreen(
            QuizPageState.Initial
//            QuizPageState.Success(
//                QuizData("a","d","Hard",listOf("a", "b", "c"),"a long Question goes here?","")
//            )
            , {}
        ) {}
    }
}