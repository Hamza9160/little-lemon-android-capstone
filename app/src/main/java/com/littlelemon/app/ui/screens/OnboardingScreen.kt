package com.littlelemon.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.littlelemon.app.data.UserPreferencesRepository
import com.littlelemon.app.ui.components.LittleLemonHeader
import com.littlelemon.app.ui.theme.CharcoalGray
import com.littlelemon.app.ui.theme.LittleLemonGreen
import com.littlelemon.app.ui.theme.LittleLemonYellow
import kotlinx.coroutines.launch

/**
 * First-run screen. Greets the user, collects first name, last name and email,
 * validates them, persists them, then navigates to Home.
 */
@Composable
fun OnboardingScreen(
    repository: UserPreferencesRepository,
    onRegistered: () -> Unit
) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        LittleLemonHeader()

        // Green banner greeting
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(LittleLemonGreen)
                .padding(vertical = 36.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Let's get to know you",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Text(
                text = "Personal information",
                style = MaterialTheme.typography.titleLarge,
                color = CharcoalGray
            )
            Spacer(Modifier.height(24.dp))

            LabeledField("First name", firstName) { firstName = it }
            Spacer(Modifier.height(16.dp))
            LabeledField("Last name", lastName) { lastName = it }
            Spacer(Modifier.height(16.dp))
            LabeledField(
                label = "Email",
                value = email,
                keyboardType = KeyboardType.Email
            ) { email = it }

            error?.let {
                Spacer(Modifier.height(12.dp))
                Text(text = it, color = MaterialTheme.colorScheme.error)
            }
        }

        Spacer(Modifier.weight(1f))

        Button(
            onClick = {
                val validationError = validate(firstName, lastName, email)
                if (validationError != null) {
                    error = validationError
                } else {
                    error = null
                    scope.launch {
                        repository.saveProfile(firstName, lastName, email)
                        onRegistered()
                    }
                }
            },
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = LittleLemonYellow,
                contentColor = CharcoalGray
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 32.dp)
                .height(50.dp)
        ) {
            Text("Register", style = MaterialTheme.typography.labelLarge)
        }
    }
}

@Composable
private fun LabeledField(
    label: String,
    value: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChange: (String) -> Unit
) {
    Column {
        Text(text = label, style = MaterialTheme.typography.bodyMedium, color = CharcoalGray)
        Spacer(Modifier.height(6.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp)
        )
    }
}

private fun validate(firstName: String, lastName: String, email: String): String? {
    if (firstName.isBlank() || lastName.isBlank() || email.isBlank()) {
        return "Please fill in all fields."
    }
    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()) {
        return "Please enter a valid email address."
    }
    return null
}
