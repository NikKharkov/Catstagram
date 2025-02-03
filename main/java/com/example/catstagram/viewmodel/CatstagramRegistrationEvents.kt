package com.example.catstagram.viewmodel

import kotlinx.datetime.LocalDate

enum class CatsGender {
    MALE,
    FEMALE
}

sealed interface CatstagramRegistration {
    object SaveCat : CatstagramRegistration
    object ClearErrors : CatstagramRegistration
    data class SetName(val name: String) : CatstagramRegistration
    data class SetBirthDate(val date: LocalDate) : CatstagramRegistration
    data class SetGender(val catsGender: CatsGender?) : CatstagramRegistration
}