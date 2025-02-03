package com.example.catstagram

import android.content.Context
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.catstagram.data.local.Cat
import com.example.catstagram.model.CatState
import com.example.catstagram.model.UserGoogleData
import com.example.catstagram.model.VideoItem
import com.example.catstagram.ui.gallery.GalleryScreen
import com.example.catstagram.ui.main.MainScreen
import com.example.catstagram.ui.registration.RegistrationScreen
import com.example.catstagram.ui.settings.SettingsScreen
import com.example.catstagram.ui.util.BottomBar
import com.example.catstagram.ui.videos.VideosScreen
import com.example.catstagram.viewmodel.CatstagramRegistration

sealed class Screen(val route: String) {
    object RegistrationScreen : Screen("registration_screen")
    object MainScreenScreen : Screen("main_screen")
    object GalleryScreen : Screen("gallery_screen")
    object VideosScreen : Screen("videos_screen")
    object SettingsScreen : Screen("settings_screen")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatstagramApp(
    catState: CatState,
    userState: UserGoogleData,
    onEvent: (CatstagramRegistration) -> Unit,
    saveImage: (Cat, Uri?) -> Unit,
    onSignInClick: () -> Unit,
    imageUris: List<Uri>,
    saveToGallery: (Uri, Context) -> Unit,
    videos: List<VideoItem>,
) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("CatstagramPrefs", Context.MODE_PRIVATE)
    val isFirstLaunch =
        remember { mutableStateOf(sharedPreferences.getBoolean("isFirstLaunch", true)) }

    val startDestination = if (isFirstLaunch.value) {
        Screen.RegistrationScreen.route
    } else {
        Screen.MainScreenScreen.route
    }

    val navController = rememberNavController()
    val currentBackStackEntry = navController.currentBackStackEntryAsState().value
    val currentRoute = currentBackStackEntry?.destination?.route

    // NavigationBar doesn't have scrollBehavior, so we use our custom component
    // for hiding NavBar while scrolling
    val bottomBarVisible = remember { mutableStateOf(true) }

    val scrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                if (available.y > 0) bottomBarVisible.value = true
                if (available.y < 0) bottomBarVisible.value = false
                return Offset.Zero
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollConnection),
        bottomBar = {
            if (currentRoute != Screen.RegistrationScreen.route) {
                AnimatedVisibility(visible = bottomBarVisible.value) {
                    BottomBar(
                        navigateToMainScreen = { navController.navigate(Screen.MainScreenScreen.route) },
                        navigateToGallery = { navController.navigate(Screen.GalleryScreen.route) },
                        navigateToRegistrationScreen = { navController.navigate(Screen.RegistrationScreen.route) },
                        navigateToVideosScreen = { navController.navigate(Screen.VideosScreen.route) },
                        navigateToSettingsScreen = { navController.navigate(Screen.SettingsScreen.route) },
                    )
                }
            }
        }

    ) { contentPaddings ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
        ) {

            composable(Screen.RegistrationScreen.route) {
                LaunchedEffect(Unit) {
                    onEvent(CatstagramRegistration.ClearErrors)
                }

                RegistrationScreen(
                    catState = catState,
                    onEvent = onEvent,
                    onContinue = {
                        sharedPreferences.edit().putBoolean("isFirstLaunch", false).apply()
                        isFirstLaunch.value = false
                        navController.navigate(Screen.MainScreenScreen.route) {
                            popUpTo(Screen.RegistrationScreen.route) { inclusive = true }
                        }
                    }
                )
            }

            composable(Screen.MainScreenScreen.route) {
                MainScreen(
                    saveImage = saveImage,
                    contentPaddingValues = contentPaddings,
                    cats = catState.cats,
                )
            }

            composable(Screen.GalleryScreen.route) {
                GalleryScreen(
                    context = context,
                    imageUris = imageUris,
                    saveToGallery = saveToGallery,
                )
            }

            composable(Screen.VideosScreen.route) {
                VideosScreen(
                    videos = videos
                )
            }

            composable(Screen.SettingsScreen.route) {
                SettingsScreen(
                    user = userState,
                    onSignInClick = onSignInClick
                )
            }
        }
    }
}