package com.example.catstagram.model

import com.example.catstagram.data.local.Cat
import com.example.catstagram.viewmodel.CatsGender
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
import network.chaintech.kmp_date_time_picker.utils.now

data class CatState(
    val name: String = "",
    val isNameError: Boolean = false,
    val birthDate: LocalDate = LocalDate.now(),
    val gender: CatsGender? = null,
    val isGenderError: Boolean = false,
    var avatarUri: Flow<String>? = null,
    val cats: List<Cat> = emptyList()
)
