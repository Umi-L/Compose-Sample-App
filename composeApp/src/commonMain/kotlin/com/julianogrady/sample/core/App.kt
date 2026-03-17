package com.julianogrady.sample.core

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.julianogrady.sample.core.domain.repository.SettingsRepository
import com.julianogrady.sample.home.presentation.homePage.HomePageScreenRoot
import com.julianogrady.sample.home.presentation.homePage.HomePageViewModel
import kotlinx.serialization.Serializable
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Serializable
object Home


// example of a page with a parameter
//@Serializable
//data class EnvelopeInfo(val envelopeId: Long)

@Composable
fun App(){
    LoadingApp(
        settingsRepository = koinInject<SettingsRepository>(),
    )
}

@Composable
fun LoadingApp(
    settingsRepository: SettingsRepository
){
    val navController = rememberNavController()

    var initialRoute: Any? by remember { mutableStateOf(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        // Update this call to match your SettingsRepository suspend function
        val ran = try {
            settingsRepository.getSettings().initialSetupRan
        } catch (t: Throwable) {
            false
        }

        // example to add a setup page for first time running
//        initialRoute = if (ran) Home else Setup

        initialRoute = Home

        isLoading = false
    }

    if (isLoading || initialRoute == null) {
        androidx.compose.foundation.layout.Box(
            modifier = androidx.compose.ui.Modifier.fillMaxSize(),
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            Text(
                text = "Loading..."
            )
        }
        return
    }

    NavHost(
        navController = navController,
        startDestination = initialRoute!!,
    ) {
        composable<Home> {
            val viewModel = koinViewModel<HomePageViewModel>()

            HomePageScreenRoot(
                viewModel = viewModel,
                // handlers are bubbled up if they require navigation.
                navigateToSettingsPage = {
                    // TODO
                }
            )
        }
        // example of a page with a parameter
//        composable<EnvelopeInfo> { backStackEntry ->
//
//            val route = backStackEntry.toRoute<EnvelopeInfo>()
//
//            val infoVm = koinViewModel<InfoPageViewModel>(
//                viewModelStoreOwner = backStackEntry,           // keep VM scoped to this destination
//                parameters = { parametersOf(route.envelopeId) }
//            )
//
//            InfoPageScreenRoot(
//                viewModel = infoVm,
//                onBackPressed = { navController.popBackStack() },
//                onEditPressed = { /* TODO */ },
//            )
//        }
    }
}
