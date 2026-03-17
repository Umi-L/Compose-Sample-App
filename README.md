This is a Kotlin Multiplatform project targeting Android, iOS, Desktop (JVM).

* [/composeApp](./composeApp/src) is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
    - [commonMain](./composeApp/src/commonMain/kotlin) is for code that’s common for all targets.
    - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
      For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
      the [iosMain](./composeApp/src/iosMain/kotlin) folder would be the right place for such calls.
      Similarly, if you want to edit the Desktop (JVM) specific part, the [jvmMain](./composeApp/src/jvmMain/kotlin)
      folder is the appropriate location.

* [/iosApp](./iosApp/iosApp) contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform,
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.

### Build and Run Android Application

To build and run the development version of the Android app, use the run configuration from the run widget
in your IDE’s toolbar or build it directly from the terminal:

- on macOS/Linux
  ```shell
  ./gradlew :composeApp:assembleDebug
  ```
- on Windows
  ```shell
  .\gradlew.bat :composeApp:assembleDebug
  ```

### Build and Run Desktop (JVM) Application

To build and run the development version of the desktop app, use the run configuration from the run widget
in your IDE’s toolbar or run it directly from the terminal:

- on macOS/Linux
  ```shell
  ./gradlew :composeApp:run
  ```
- on Windows
  ```shell
  .\gradlew.bat :composeApp:run
  ```

### Build and Run iOS Application

To build and run the development version of the iOS app, use the run configuration from the run widget
in your IDE’s toolbar or open the [/iosApp](./iosApp) directory in Xcode and run it from there.

---

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…

### Adding Dependencies
To add dependencies to the project add them to the build.gradle.kts file.

For example, in this project SQLDelight is used for database management. To add SQLDelight to the project, add the following lines to the build.gradle.kts file:

```kotlin
plugins {
    // ...
    alias(libs.plugins.sqldelight)
}

sourceSets {

    // ...

    // platform specific dependencies
    iosMain.dependencies {
        implementation(libs.sqldelight.nativeDriver)
    }
    
    androidMain.dependencies {
        implementation(compose.preview)
        implementation(libs.androidx.activity.compose)

        implementation(libs.sqldelight.androidDriver)
    }
    
    // ...

    // common dependencies
    commonMain.dependencies {
        // ... 
        implementation(libs.sqldelight.coroutines)
    }
}
```

After a dependency is added to reload the project and sync the dependencies:
- in intellij, click the 'sync gradle changes' button that appears
- in the terminal, run the following command:
  - on macOS/Linux
    ```shell
    ./gradlew :composeApp:sync
    ```
  - on Windows
    ```shell
    .\gradlew.bat :composeApp:sync
    ```

## Architecture

This project is structured using the MVI (Model-View-Intent) architecture pattern.
Code is primarily shared in commonMain src [here](composeApp/src/commonMain/kotlin/com/julianogrady/sample).
Within that folder is the [core](composeApp/src/commonMain/kotlin/com/julianogrady/sample/core) and [di](composeApp/src/commonMain/kotlin/com/julianogrady/sample/di) folders which handle the core logic and dependency injection of the app respectively.
Furthermore, a folder is made for each screen in the app, such as [home](composeApp/src/commonMain/kotlin/com/julianogrady/sample/home).
Each screen folder contains 3 subfolders:
- [data](composeApp/src/commonMain/kotlin/com/julianogrady/sample/home/data) for data models and repositories
- [domain](composeApp/src/commonMain/kotlin/com/julianogrady/sample/home/domain) for use cases and business logic
- [presentation](composeApp/src/commonMain/kotlin/com/julianogrady/sample/home/presentation) for UI components and state management

### Dependency Injection
This project uses Koin for dependency injection. The DI module is located in the [di](composeApp/src/commonMain/kotlin/com/julianogrady/sample/di) folder.
The DI module is where you can define your dependencies and how they are provided throughout the app.
Specifically, modules are defined in the [Modules.kt](composeApp/src/commonMain/kotlin/com/julianogrady/sample/di/Modules.kt) file.
The modules file has two items defined `sharedModule` and `platformModule`. The `sharedModule` is for dependencies that are shared across all platforms, while the `platformModule` is for dependencies that are specific to a platform.
The `platformModule` is expected to be defined in the platform specific source sets, as done in [Modules.android.kt](composeApp/src/androidMain/kotlin/com/julianogrady/sample/di/Modules.android.kt), [Modules.ios.kt](composeApp/src/iosMain/kotlin/com/julianogrady/sample/di/Modules.ios.kt), and [Modules.jvm.kt](composeApp/src/jvmMain/kotlin/com/julianogrady/sample/di/Modules.jvm.kt).

### Database
This project uses SQLDelight for database management. The database is defined in the [AppDatabase.sq](composeApp/src/commonMain/sqldelight/com/julianogrady/sample/AppDatabase.sq) file.
This is initialized as done in the DI module files as mentioned in the section above.
Database queries are called cross-platform using providers as done in the [SettingsRepository.kt](composeApp/src/commonMain/kotlin/com/julianogrady/sample/core/domain/repository/SettingsRepository.kt) interface which is implemented using SQLDelight in [SQLDelightSettingsRepository.kt](composeApp/src/commonMain/kotlin/com/julianogrady/sample/core/data/repository/SQLDelightSettingsRepository.kt).

### Routing
This project uses standard androidx navigation for routing. Pages are defined using serializable data classes in the entrypoint [App.kt](composeApp/src/commonMain/kotlin/com/julianogrady/sample/core/App.kt) file.

### Theming
This project uses a custom theme defined in the [Theme.kt](composeApp/src/commonMain/kotlin/com/julianogrady/sample/core/theming/Theme.kt) file.
The theme wraps the whole project and is applied in the entrypoint [App.kt](composeApp/src/commonMain/kotlin/com/julianogrady/sample/core/App.kt) file. You can customize the theme by editing the colors, typography, and shapes defined in the [Theme.kt](composeApp/src/commonMain/kotlin/com/julianogrady/sample/core/theming/Theme.kt) file.

### Pages

This project organizes pages by feature. A typical feature folder structure looks like this:

```
featureName/
    data/           // Repositories, Data Sources
    domain/         // Models, Use Cases, Repository Interfaces
    presentation/   // UI Components, ViewModels, States, Actions
```

### Adding a New Page

To add a new page (e.g., `ProfilePage`), follow these steps:

1.  **Create the Directory Structure**
    Create a new package under `com.julianogrady.sample` (e.g., `profile`). Inside, create `presentation/profilePage`.

2.  **Define the UI State**
    Create a `ProfilePageState.kt` data class to hold the screen's state.
    ```kotlin
    data class ProfilePageState(
        val isLoading: Boolean = false,
        val username: String = ""
    )
    ```

3.  **Define Actions**
    Create a `ProfilePageAction.kt` sealed interface for user interactions.
    ```kotlin
    sealed interface ProfilePageAction {
        data class OnUpdateUsername(val newName: String) : ProfilePageAction
        data object OnSave : ProfilePageAction
    }
    ```

4.  **Create the ViewModel**
    Create specific `ProfilePageViewModel.kt` extending `ViewModel`.
    ```kotlin
    class ProfilePageViewModel(
        private val repository: UserRepository // Example dependency
    ) : ViewModel() {
        private val _state = MutableStateFlow(ProfilePageState())
        val state = _state.asStateFlow()

        fun onAction(action: ProfilePageAction) {
            when(action) {
                is ProfilePageAction.OnUpdateUsername -> {
                    _state.update { it.copy(username = action.newName) }
                }
                ProfilePageAction.OnSave -> {
                    // Perform save operation
                }
            }
        }
    }
    ```

5.  **Create the Screen Composable**
    Create `ProfilePageScreen.kt`. Use the "Root" pattern to separate Koin injection from the UI.
    ```kotlin
    @Composable
    fun ProfilePageScreenRoot(
        viewModel: ProfilePageViewModel = koinViewModel(),
        onNavigateBack: () -> Unit
    ) {
        val state by viewModel.state.collectAsStateWithLifecycle()
        ProfilePageScreen(
            state = state,
            onAction = viewModel::onAction,
            onNavigateBack = onNavigateBack
        )
    }

    @Composable
    private fun ProfilePageScreen(
        state: ProfilePageState,
        onAction: (ProfilePageAction) -> Unit,
        onNavigateBack: () -> Unit
    ) {
        // Your UI code here
    }
    ```

6.  **Define the Route**
    In `App.kt` (or a dedicated routes file), define a standard Serializable object (or data class for arguments).
    ```kotlin
    @Serializable
    object Profile
    ```

7.  **Register the Route**
    In `App.kt`, add the route to the `NavHost`.
    ```kotlin
    NavHost(navController = navController, startDestination = Home) {
        // ... existing routes
        composable<Profile> {
            ProfilePageScreenRoot(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
    ```

8.  **Register ViewModel in Koin**
    In `di/Modules.kt`, add the ViewModel to the `sharedModule`.
    ```kotlin
    val sharedModule = module {
        // ...
        viewModelOf(::ProfilePageViewModel)
    }
    ```
