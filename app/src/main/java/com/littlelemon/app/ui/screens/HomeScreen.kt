package com.littlelemon.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.littlelemon.app.data.MenuItem
import com.littlelemon.app.data.MenuRepository
import com.littlelemon.app.ui.components.LittleLemonHeader
import com.littlelemon.app.ui.theme.CharcoalGray
import com.littlelemon.app.ui.theme.CloudGray
import com.littlelemon.app.ui.theme.LittleLemonGreen
import com.littlelemon.app.ui.theme.LittleLemonYellow
import com.littlelemon.app.ui.theme.White

/**
 * Home screen. Layout from top to bottom:
 *  - Header (logo + profile avatar)
 *  - Hero (heading, subheading, about text, image, search bar)
 *  - Menu breakdown (delivery text + category filter buttons)
 *  - Food menu list (vertically scrollable summarized dishes)
 */
@Composable
fun HomeScreen(onProfileClick: () -> Unit) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<String?>(null) }

    val menu = MenuRepository.items.filter { item ->
        val matchesSearch = searchQuery.isBlank() ||
            item.title.contains(searchQuery, ignoreCase = true)
        val matchesCategory = selectedCategory == null ||
            item.category.equals(selectedCategory, ignoreCase = true)
        matchesSearch && matchesCategory
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(White)) {
        LittleLemonHeader(showProfileIcon = true, onProfileClick = onProfileClick)

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                HeroSection(
                    searchQuery = searchQuery,
                    onSearchChange = { searchQuery = it }
                )
            }
            item {
                MenuBreakdown(
                    selectedCategory = selectedCategory,
                    onCategorySelected = { category ->
                        selectedCategory = if (selectedCategory == category) null else category
                    }
                )
            }
            items(menu, key = { it.id }) { item ->
                MenuRow(item)
                Divider(color = CloudGray, thickness = 1.dp)
            }
        }
    }
}

@Composable
private fun HeroSection(searchQuery: String, onSearchChange: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(LittleLemonGreen)
            .padding(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Little Lemon",
                    style = MaterialTheme.typography.displayMedium,
                    color = LittleLemonYellow
                )
                Text(
                    text = "Chicago",
                    style = MaterialTheme.typography.headlineMedium,
                    color = White
                )
                Spacer(Modifier.height(12.dp))
                Text(
                    text = "We are a family owned Mediterranean restaurant, focused on traditional recipes served with a modern twist.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = White
                )
            }
            Spacer(Modifier.width(12.dp))
            AsyncImage(
                model = "https://images.unsplash.com/photo-1565299624946-b28f40a0ae38?w=400&q=80",
                contentDescription = "Little Lemon dishes",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
        }

        Spacer(Modifier.height(16.dp))

        // Search bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchChange,
            placeholder = { Text("Enter search phrase") },
            leadingIcon = {
                Icon(Icons.Filled.Search, contentDescription = "Search", tint = CharcoalGray)
            },
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = White,
                unfocusedContainerColor = White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun MenuBreakdown(
    selectedCategory: String?,
    onCategorySelected: (String) -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "ORDER FOR DELIVERY!",
            style = MaterialTheme.typography.titleMedium,
            color = CharcoalGray
        )
        Spacer(Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MenuRepository.categories.forEach { category ->
                CategoryChip(
                    label = category,
                    selected = selectedCategory == category,
                    onClick = { onCategorySelected(category) }
                )
            }
        }
    }
    Divider(color = CloudGray, thickness = 1.dp)
}

@Composable
private fun CategoryChip(label: String, selected: Boolean, onClick: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = if (selected) LittleLemonGreen else CloudGray,
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = if (selected) White else LittleLemonGreen,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
        )
    }
}

@Composable
private fun MenuRow(item: MenuItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleMedium,
                color = CharcoalGray
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = item.description,
                style = MaterialTheme.typography.bodyMedium,
                color = CharcoalGray,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "$${item.price}",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = LittleLemonGreen
            )
        }
        Spacer(Modifier.width(12.dp))
        AsyncImage(
            model = item.imageUrl,
            contentDescription = item.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(90.dp)
                .clip(RoundedCornerShape(12.dp))
        )
    }
}
