package com.darshan.miskin.quizapp_client.presentation.ui.screens

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.IBinder
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
        if(::iQuizService.isInitialized){
            //TODO: Reload quiz
        }
        else {
            try {
                val isServerInstalled = this@MainActivity.packageManager.getPackageInfo(
                    "com.darshan.miskin.quizapp_server",
                    0
                )
                if (isServerInstalled != null) {
                    viewModel.setQuizPageState(QuizPageState.Loading)
                    val intent = Intent("com.darshan.miskin.ACTION_START_QUIZ").apply {
                        setPackage("com.darshan.miskin.quizapp_server")
                        action = "com.darshan.miskin.ACTION_START_QUIZ"
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
        }
    }

    lateinit var iQuizService: IQuizDataInterface
    val iQuizCallBackInterface = object : IQuizCallBackInterface.Stub() {
        override fun onQuizLoaded() {
            nextQuestion()
        }

        override fun onQuizComplete(isComplete: Boolean) {
            viewModel.setQuizPageState(QuizPageState.Initial)
        }
    }

    val connection = object : ServiceConnection {
        override fun onServiceConnected(
            name: ComponentName?,
            service: IBinder?
        ) {
            iQuizService = IQuizDataInterface.Stub.asInterface(service)
            iQuizService.registerQuizCallback(iQuizCallBackInterface)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
//            iQuizService.unregisterQuizCallback(iQuizCallBackInterface)
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
//                QuizData(
//                    "a",
//                    "d",
//                    "Hard",
//                    listOf("a", "b", "c"),
//                    "a long Question goes here?",
//                    ""
//                )
//            )
            , {}
        ) {}
    }
}