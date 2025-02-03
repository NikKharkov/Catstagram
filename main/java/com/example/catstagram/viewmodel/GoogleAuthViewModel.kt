package com.example.catstagram.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catstagram.BuildConfig
import com.example.catstagram.model.UserGoogleData
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.Firebase
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class GoogleAuthViewModel(
    private val oneTapClient: SignInClient,
) : ViewModel() {

    private val auth = Firebase.auth

    private val _user = MutableStateFlow<UserGoogleData?>(null)
    val user = _user.asStateFlow()

    init {
        _user.value = getSignedInUser()
    }

    fun oneTapSignIn(launcher: ActivityResultLauncher<IntentSenderRequest>, context: Context) {
        viewModelScope.launch {
            try {
                val result = oneTapClient.beginSignIn(buildSignInRequest(context)).await()
                val intentSenderRequest = IntentSenderRequest.Builder(result.pendingIntent).build()
                launcher.launch(intentSenderRequest)
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Error starting sign-in: ${e.message}")
            }
        }
    }

    fun signIn(intent: Intent) {
        viewModelScope.launch {
            val credential = oneTapClient.getSignInCredentialFromIntent(intent)
            val googleIdToken = credential.googleIdToken
            val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)
            try {
                val user = auth.signInWithCredential(googleCredentials).await().user
                _user.value = UserGoogleData(
                    id = user?.uid ?: "",
                    name = user?.displayName ?: "stringResource(R.string.guest)",
                    profilePicture = user?.photoUrl?.toString()
                )
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Sign-in failed: ${e.message}")
                _user.value = null
            }
        }
    }

    private fun getSignedInUser(): UserGoogleData? = auth.currentUser?.run {
        UserGoogleData(
            id = uid,
            name = displayName ?: "Guest",
            profilePicture = photoUrl?.toString()
        )
    }


    private fun buildSignInRequest(context: Context): BeginSignInRequest {
        return BeginSignInRequest.Builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(BuildConfig.USER_WEB_KEY)
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .build()
    }

    private fun readApiKeyFromAssets(context: Context): String {
        return context.assets.open("USER_WEB_KEY.txt").bufferedReader().use { it.readText() }
    }

}