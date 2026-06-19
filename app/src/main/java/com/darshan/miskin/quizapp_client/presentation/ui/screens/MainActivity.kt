package com.darshan.miskin.quizapp_client.presentation.ui.screens

import android.content.ComponentName
import android.content.Context
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.darshan.miskin.quizapp_client.IQuizCompleteInterface
import com.darshan.miskin.quizapp_client.IQuizDataInterface
import com.darshan.miskin.quizapp_client.presentation.model.QuizPageState
import com.darshan.miskin.quizapp_client.presentation.ui.theme.QuizApp_ClientTheme

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            QuizApp_ClientTheme {
                val quizPageState = viewModel.quizPageState.collectAsStateWithLifecycle()
                MainScreen(
                    quizPageState = quizPageState.value,
                    getNextQuestion = {
                        val quizData = iQuizService.nextQuestion
                        if (quizData != null) {
                            viewModel.setQuizPageState(QuizPageState.Success(quizData))
                        } else {
                            viewModel.setQuizPageState(QuizPageState.Error)
                        }
                    }
                ) {

                    try {
                        val isServerInstalled = this@MainActivity.packageManager.getPackageInfo("com.darshan.miskin.quizapp_server", 0)
                        if (isServerInstalled != null){
                            viewModel.setQuizPageState(QuizPageState.Loading)
                            val intent = Intent("com.darshan.miskin.ACTION_START_QUIZ").apply {
                                setPackage("com.darshan.miskin.quizapp_server")
                                action = "com.darshan.miskin.ACTION_START_QUIZ"
                            }
                            bindService(intent, connection, BIND_AUTO_CREATE)
                        }
                    }
                    catch (e: PackageManager.NameNotFoundException){
                        Toast.makeText(this@MainActivity, "Quiz Server App Not Installed!", Toast.LENGTH_SHORT).show()
                        return@MainScreen
                    }
                }
            }
        }
    }

    lateinit var iQuizService: IQuizDataInterface
    var iQuizCompleteInterface = object : IQuizCompleteInterface.Stub() {
        override fun onQuizComplete(isComplete: Boolean) {
            viewModel.setQuizPageState(QuizPageState.Initial)
            Toast.makeText(this@MainActivity, "Quiz Completed!", Toast.LENGTH_LONG).show()
        }

    }

    val connection = object : ServiceConnection {
        override fun onServiceConnected(
            name: ComponentName?,
            service: IBinder?
        ) {
            iQuizService = IQuizDataInterface.Stub.asInterface(service)
            iQuizService.registerQuizCallback(iQuizCompleteInterface)
            iQuizService.nextQuestion?.let {
                Toast.makeText(this@MainActivity, "Question Fetched!", Toast.LENGTH_SHORT).show()
                viewModel.setQuizPageState(QuizPageState.Success(it))
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            iQuizService.unregisterQuizCallback(iQuizCompleteInterface)
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