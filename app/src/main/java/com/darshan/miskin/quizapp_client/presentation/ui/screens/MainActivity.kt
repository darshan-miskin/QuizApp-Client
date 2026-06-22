package com.darshan.miskin.quizapp_client.presentation.ui.screens

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.IBinder
import android.os.RemoteException
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.darshan.miskin.quizapp_client.R
import com.darshan.miskin.quizapp_client.contract.QuizContract
import com.darshan.miskin.quizapp_server.IQuizDataInterface
import com.darshan.miskin.quizapp_client.presentation.model.QuizPageState
import com.darshan.miskin.quizapp_client.presentation.ui.theme.QuizApp_ClientTheme
import com.darshan.miskin.quizapp_server.IQuizCallBackInterface

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    var iQuizService: IQuizDataInterface? = null
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
            try {
                iQuizService?.registerQuizCallback(iQuizCallBackInterface)
                iQuizService?.startQuiz()
            } catch (e: RemoteException) {
                handleException()
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            /*viewModel.setQuizPageState(QuizPageState.Waiting) */ //TODO: Restore client state from server db.
            handleException()
        }
    }

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

    override fun onDestroy() {
        if (iQuizService != null) {
            try {
                iQuizService?.unregisterQuizCallback(iQuizCallBackInterface)
            } catch (e: RemoteException) { }
            unbindService(connection)
            iQuizService = null
        }
        super.onDestroy()
    }

    fun nextQuestion() {
        try {
            iQuizService?.nextQuestion?.let {
                viewModel.setQuizPageState(QuizPageState.Success(it))
            }
        } catch (e: RemoteException) {
            handleException()
        }
    }

    fun startQuiz() {
        if (iQuizService == null) {
            bindIfServerInstalled()
        } else {
            try {
                viewModel.setQuizPageState(QuizPageState.Loading)
                iQuizService?.startQuiz()
            } catch (e: RemoteException) {
                handleException()
            }
        }
    }

    fun handleException() {
        viewModel.setQuizPageState(QuizPageState.Error)
        iQuizService = null
    }

    fun bindIfServerInstalled() {
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
                getString(R.string.quiz_server_app_not_installed),
                Toast.LENGTH_SHORT
            ).show()
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
//                QuizData()
//            )
            , {}
        ) {}
    }
}