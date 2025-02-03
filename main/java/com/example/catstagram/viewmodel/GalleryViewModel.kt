package com.example.catstagram.viewmodel

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catstagram.R
import com.google.android.gms.tasks.Tasks
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class GalleryViewModel: ViewModel() {

    private val storageRef = FirebaseStorage.getInstance().getReference().child("images")
    private val auth = Firebase.auth

    private val _galleryState = MutableStateFlow<List<Uri>>(emptyList())
    val galleryState: StateFlow<List<Uri>> = _galleryState.asStateFlow()

    init {
        viewModelScope.launch {
            loadGalleryImages()
        }
    }

    fun saveImagesToGallery(uri: Uri, context: Context) {
        val uniqueImageId = UUID.randomUUID()
        val userId = auth.currentUser?.uid ?: return
        val imageRef = storageRef.child("/$userId/$uniqueImageId")

        val byteArray: ByteArray? = context.contentResolver
            .openInputStream(uri)
            ?.use { it.readBytes() }

        byteArray?.let {
            val uploadingTask = imageRef.putBytes(byteArray)

            uploadingTask.addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                    _galleryState.value = _galleryState.value + downloadUri
                }
            }.addOnFailureListener {
                Toast.makeText(
                    context,
                    context.getString(R.string.failure),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun loadGalleryImages() {
        storageRef.child("/${auth.currentUser?.uid}").listAll().addOnSuccessListener { listResult ->
            val imageUrls = mutableListOf<Uri>()
            val tasks = listResult.items.map { item ->
                item.downloadUrl.addOnSuccessListener { uri ->
                    imageUrls.add(uri)
                }
            }
            Tasks.whenAllComplete(tasks).addOnSuccessListener {
                _galleryState.value = imageUrls
            }
        }
    }

}