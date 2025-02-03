package com.example.catstagram

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.catstagram.model.UserGoogleData
import com.example.catstagram.ui.theme.CatstagramTheme
import com.example.catstagram.viewmodel.CatViewModel
import com.example.catstagram.viewmodel.GalleryViewModel
import com.example.catstagram.viewmodel.GoogleAuthViewModel
import com.example.catstagram.viewmodel.YouTubeViewModel
import com.google.android.gms.auth.api.identity.Identity
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    private lateinit var authViewModel: GoogleAuthViewModel
    private lateinit var signInLauncher: ActivityResultLauncher<IntentSenderRequest>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        authViewModel = GoogleAuthViewModel(Identity.getSignInClient(this))

        signInLauncher =
            registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    result.data?.let { authViewModel.signIn(it) }
                }
            }

        setContent {
            CatstagramTheme {
                val context = LocalContext.current
                val catViewModel: CatViewModel = koinViewModel()
                val galleryViewModel: GalleryViewModel = viewModel()
                val youTubeViewModel: YouTubeViewModel = koinViewModel()
                val catState by catViewModel.catState.collectAsState()
                val galleryState by galleryViewModel.galleryState.collectAsState()
                val videos by youTubeViewModel.videos.collectAsState()
                val user by authViewModel.user.collectAsState()

                CatstagramApp(
                    catState = catState,
                    onEvent = catViewModel::onEvent,
                    saveImage = { cat, uri ->
                        catViewModel.saveProfilePicture(cat, context, uri)
                    },
                    onSignInClick = { authViewModel.oneTapSignIn(signInLauncher, context) },
                    imageUris = galleryState,
                    saveToGallery = { uri, context ->
                        galleryViewModel.saveImagesToGallery(uri, context)
                    },
                    videos = videos,
                    userState = user ?: UserGoogleData("", stringResource(R.string.guest), null)
                )
            }
        }
    }
}



