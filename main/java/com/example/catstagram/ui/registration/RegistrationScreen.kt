package com.example.catstagram.ui.registration

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.catstagram.R
import com.example.catstagram.viewmodel.CatstagramRegistration
import com.example.catstagram.model.CatState
import com.example.catstagram.ui.registration.components.ChooseDateTextField
import com.example.catstagram.ui.registration.components.ChooseGenderField
import com.example.catstagram.ui.registration.components.GifAnimation
import com.example.catstagram.ui.registration.components.InputField
import com.example.catstagram.ui.registration.components.NavigationButton

@Composable
fun RegistrationScreen(
    catState: CatState,
    onEvent: (CatstagramRegistration) -> Unit,
    onContinue: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE6E6FA))
    ) {
        Spacer(modifier = Modifier.padding(vertical = 40.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            GifAnimation(
                drawable = R.drawable.neko_greeting,
                modifier = Modifier
                    .size(300.dp)
                    .padding(16.dp)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            InputField(
                value = catState.name,
                onValueChange = { name -> onEvent(CatstagramRegistration.SetName(name)) },
                isError = catState.isNameError
            )
        }
        ChooseGenderField(
            selectedGender = catState.gender,
            isError = catState.isGenderError,
            onGenderSelected = { gender -> onEvent(CatstagramRegistration.SetGender(gender)) }
        )
        ChooseDateTextField(
            birthDate = catState.birthDate,
            onSetBirthDate = { date -> onEvent(CatstagramRegistration.SetBirthDate(date)) }
        )
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            NavigationButton(
                onClick = {
                    onEvent(CatstagramRegistration.SaveCat)
                    if (catState.name.isNotEmpty() && catState.gender != null) {
                        onContinue()
                    }
                }
            )
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
fun RegistrationScreenPreview() {
    RegistrationScreen(
        catState = CatState(),
        onEvent = {},
        onContinue = {}
    )
}
