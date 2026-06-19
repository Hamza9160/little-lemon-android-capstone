package com.littlelemon.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.littlelemon.app.data.UserPreferencesRepository
import com.littlelemon.app.data.UserProfile
import com.littlelemon.app.ui.components.LittleLemonHeader
import com.littlelemon.app.ui.theme.CharcoalGray
import com.littlelemon.app.ui.theme.LittleLemonGreen
import com.littlelemon.app.ui.theme.LittleLemonYellow
import kotlinx.coroutines.launch

/**
 * Profile screen. Shows the data captured during onboarding (read straight from
 * DataStore, so it survives restarts) and a Log out button that clears all data.
 */
@Composable
fun ProfileScreen(
    repository: UserPreferencesRepository,
    onBack: () -> Unit,
    onLoggedOut: () -> Unit
) {
    val profile by repository.userProfile.collectAsStateWithLifecycle(initialValue = UserProfile())
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)) {

        // Header with a back button (stack navigation).
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = LittleLemonGreen
                )
            }
        }
        LittleLemonHeader()

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

            ReadOnlyField("First name", profile.firstName)
            Spacer(Modifier.height(16.dp))
            ReadOnlyField("Last name", profile.lastName)
            Spacer(Modifier.height(16.dp))
            ReadOnlyField("Email", profile.email)
        }

        Spacer(Modifier.weight(1f))

        Button(
            onClick = {
                scope.launch {
                    repository.clear()
                    onLoggedOut()
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
            Text("Log out", style = MaterialTheme.typography.labelLarge)
        }
    }
}

@Composable
private fun ReadOnlyField(label: String, value: String) {
    Column {
        Text(text = label, style = MaterialTheme.typography.bodyMedium, color = CharcoalGray)
        Spacer(Modifier.height(6.dp))
        OutlinedTextField(
            value = value,
            onValueChange = {},
            readOnly = true,
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth()
        )
    }
}
