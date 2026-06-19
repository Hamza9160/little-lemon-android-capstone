package com.littlelemon.app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.littlelemon.app.R
import com.littlelemon.app.ui.theme.CloudGray
import com.littlelemon.app.ui.theme.LittleLemonGreen

/**
 * Shared top header: Little Lemon logo (centered) and an optional
 * profile avatar on the right. Used on Home; the avatar is hidden elsewhere.
 */
@Composable
fun LittleLemonHeader(
    modifier: Modifier = Modifier,
    showProfileIcon: Boolean = false,
    onProfileClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left spacer balances the right-side avatar to keep the logo centered.
        Spacer(modifier = Modifier.size(44.dp))

        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_lemon),
                contentDescription = "Little Lemon logo",
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = "LITTLE LEMON",
                color = LittleLemonGreen,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                letterSpacing = 2.sp,
                textAlign = TextAlign.Center
            )
        }

        if (showProfileIcon) {
            Surface(
                shape = CircleShape,
                color = LittleLemonGreen,
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .clickable { onProfileClick() }
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "Profile",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        } else {
            Spacer(modifier = Modifier.size(44.dp))
        }
    }
    Surface(color = CloudGray, modifier = Modifier
        .fillMaxWidth()
        .size(1.dp)) {}
}
