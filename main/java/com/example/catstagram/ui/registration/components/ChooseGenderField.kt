package com.example.catstagram.ui.registration.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.catstagram.R
import com.example.catstagram.viewmodel.CatsGender

@Composable
fun ChooseGenderField(
    selectedGender: CatsGender?,
    isError: Boolean,
    onGenderSelected: (CatsGender) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CatsGender.entries.forEach { gender ->
            GenderItem(
                gender = gender,
                isSelected = selectedGender == gender,
                onSelect = { onGenderSelected(gender) }
            )
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isError) {
            Text(
                text = stringResource(R.string.choose_gender),
                color = Color(0xFFB22222),
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}

@Composable
fun GenderItem(
    gender: CatsGender,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        RadioButton(selected = isSelected, onClick = onSelect)
        Icon(
            painter = painterResource(
                if (gender == CatsGender.FEMALE) R.drawable.female_icon else R.drawable.male_icon
            ),
            modifier = Modifier.size(24.dp),
            tint = Color.Unspecified,
            contentDescription = null
        )
        Text(
            text = stringResource(
                if (gender == CatsGender.FEMALE) R.string.woman else R.string.man
            )
        )
    }
}

