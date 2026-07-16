![Architecture Diagram](https://raw.githubusercontent.com/darshan-miskin/storage/refs/heads/master/Quiz%20App%20Architecture.png)

# QuizApp Client

QuizApp Client is a modern Android application built using **Jetpack Compose**. It serves as a frontend interface for a quiz system, where the quiz data and logic are managed by a separate [**QuizApp Server**](https://github.com/darshan-miskin/QuizApp-Server) application. The two apps communicate seamlessly using **AIDL (Android Interface Definition Language)** for Inter-Process Communication (IPC).

## 🚀 Key Features

-   **Dynamic Quiz Interface**: Fetches and displays quiz questions and answers in real-time from the server.
-   **Inter-Process Communication (IPC)**: Utilizes AIDL to bind to a remote service, register callbacks, and receive updates.
-   **State Management**: Implements a robust state machine (`Initial`, `Loading`, `Success`, `Error`) to handle various application states.
-   **Modern UI/UX**: Built entirely with Jetpack Compose and Material 3, featuring:
    -   Edge-to-edge support.
    -   Responsive navigation with a Bottom Navigation Bar.
    -   Smooth transitions between Quiz and Result screens.
-   **Resilience**: Automatically detects if the Server app is installed and handles connection exceptions gracefully.

## 🛠 Tech Stack

-   **Language**: Kotlin
-   **UI Framework**: Jetpack Compose (Material 3)
-   **Navigation**: Navigation Compose
-   **Architecture**: MVVM (ViewModel, StateFlow)
-   **IPC**: AIDL (Android Interface Definition Language)
-   **Lifecycle**: Lifecycle Runtime KTX (collectAsStateWithLifecycle)

## 🏗 Project Structure

-   `presentation.ui.screens`: Contains the main UI components (`MainActivity`, `MainScreen`, `QuizScreen`, `ResultScreen`).
-   `presentation.model`: Defines the data models and UI state wrappers.
-   `contract`: Contains the `QuizContract` which defines the constants used for IPC (Action strings, Package names).
-   `aidl`: The `.aidl` files defining the interface between Client and Server.

## 🔧 How It Works

1.  **Check Installation**: Upon starting, the app checks if the Quiz Server is installed via `PackageManager`.
2.  **Bind Service**: If installed, it binds to the server's service using the intent action `com.darshan.miskin.ACTION_START_QUIZ`.
3.  **Register Callbacks**: Once connected, it registers a callback interface (`IQuizCallBackInterface`) to receive notifications like `onQuizLoaded` or `onQuizComplete`.
4.  **Quiz Flow**:
    -   User clicks on the 'Start Quiz' Button.
    -   Server is notified to Fetch Quiz Data from remote Api.
    -   The server pushes the first question.
    -   The user selects an answer, and the client requests the `nextQuestion()` from the server.
    -   Results are tracked locally in the client as the quiz progresses.

## 📦 Prerequisites

To use this application, you **must** have the [QuizApp Server](https://github.com/darshan-miskin/QuizApp-Server) installed on the same device.
