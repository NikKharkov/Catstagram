package com.example.catstagram.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catstagram.data.local.Cat
import com.example.catstagram.data.repositories.CatRepository
import com.example.catstagram.model.CatState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import network.chaintech.kmp_date_time_picker.utils.now
import java.io.File

class CatViewModel(
    private val catRepository: CatRepository
) : ViewModel() {

    private val _catState = MutableStateFlow(CatState())
    val catState: StateFlow<CatState> = _catState.asStateFlow()

    init {
        viewModelScope.launch {
            getCats().collect { cats ->
                _catState.value = _catState.value.copy(cats = cats)
            }
        }
    }

    fun saveProfilePicture(cat: Cat, context: Context, uri: Uri?) {
        if (uri == null) return

        val fileName = "avatar_${cat.id}.jpg"
        val savedUri = saveImageToInternalStorage(uri, fileName, context)

        if (savedUri != null) {
            viewModelScope.launch {
                saveUri(fileName, cat.id)
                updateCatAvatar(cat.id, fileName)
            }
        }
    }

    private fun updateCatAvatar(catId: Int, avatarUri: String) {
        viewModelScope.launch {
            val updatedCats = _catState.value.cats.map { cat ->
                if (cat.id == catId) cat.copy(avatarUri = avatarUri) else cat
            }
            _catState.value = _catState.value.copy(cats = updatedCats)
        }
    }

    private suspend fun saveUri(fileName: String, id: Int) = catRepository.saveUri(fileName, id)

    private fun getCats(): Flow<List<Cat>> = catRepository.getCats()

    private fun saveImageToInternalStorage(uri: Uri, fileName: String, context: Context): Uri? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val file = File(context.filesDir, fileName)
            inputStream?.use { input ->
                file.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
            Uri.fromFile(file)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun onEvent(event: CatstagramRegistration) {
        _catState.update {
            when (event) {
                is CatstagramRegistration.SaveCat -> {
                    validateName()
                    validateGender()
                    if (!_catState.value.isNameError || !_catState.value.isGenderError) {
                        saveCat(it)
                    }
                    it
                }

                is CatstagramRegistration.SetBirthDate -> it.copy(birthDate = event.date)
                is CatstagramRegistration.SetGender -> it.copy(gender = event.catsGender)
                is CatstagramRegistration.SetName -> it.copy(name = event.name)
                is CatstagramRegistration.ClearErrors -> it.copy(
                    isNameError = false,
                    isGenderError = false
                )
            }
        }
    }

    private fun validateName() {
        val nameError = _catState.value.name.isEmpty()
        _catState.value = _catState.value.copy(
            isNameError = nameError
        )
    }

    private fun validateGender() {
        val genderError = _catState.value.gender == null
        _catState.value = _catState.value.copy(
            isGenderError = genderError
        )
    }

    private fun saveCat(state: CatState) {
        if (state.name.isBlank() || state.gender == null) return
        val cat = Cat(
            name = state.name,
            birthDate = state.birthDate,
            gender = if (state.gender == CatsGender.MALE) 'M' else 'F',
        )
        viewModelScope.launch {
            catRepository.insertCat(cat)
        }

        _catState.update {
            it.copy(
                name = "",
                birthDate = LocalDate.now(),
                gender = null,
                isNameError = false,
                isGenderError = false
            )
        }
    }
}
