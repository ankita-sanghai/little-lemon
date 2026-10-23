package com.example.littlelemon.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.util.Locale
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.littlelemon.AppDatabase
import com.example.littlelemon.MenuItemRoom
import com.example.littlelemon.R

@Composable
fun Home(navController: NavHostController, database: AppDatabase) {
    val menuItems by remember(database) { database.menuItemDao().getAll() }
        .observeAsState(emptyList())
    HomeContent(navController, menuItems)
}

@Composable
fun HomeContent(navController: NavHostController, menuItems: List<MenuItemRoom>) {
    Column(modifier = Modifier.fillMaxSize()) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Little Lemon logo",
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .height(48.dp)
                    .align(Alignment.Center)
            )
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "Profile",
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.CenterEnd)
                    .clickable { navController.navigate(Profile.route) }
            )
        }

        // Hero section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF495E57))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Little Lemon",
                    color = Color(0xFFF4CE14),
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(text = "Chicago", color = Color.White, fontSize = 24.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "We are a family-owned Mediterranean restaurant, focused on traditional recipes served with a modern twist",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Image(
                painter = painterResource(id = R.drawable.heroimage),
                contentDescription = "Hero image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
        }

        MenuItems(menuItems)
    }
}

@Composable
fun MenuItems(items: List<MenuItemRoom>) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(items, key = { it.id }) { item ->
            MenuItem(item)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuItem(item: MenuItemRoom) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = item.title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(
                text = item.description,
                fontSize = 14.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Text(text = "$%.2f".format(item.price), fontSize = 14.sp)
        }
        Spacer(modifier = Modifier.width(12.dp))
        GlideImage(
            model = item.image,
            contentDescription = item.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(8.dp))
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    HomeContent(
        navController = rememberNavController(),
        menuItems = listOf(
            MenuItemRoom(1, "Greek Salad", "The famous greek salad of crispy lettuce, peppers, olives.", 12.99, "", "starters")
        )
    )
}

@Composable
fun HomeContent(navController: NavHostController, menuItems: List<MenuItemRoom>) {
    var searchPhrase by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<String?>(null) }

    val categories = menuItems.map { it.category }.distinct()

    val filteredItems = menuItems
        .filter { selectedCategory == null || it.category == selectedCategory }
        .filter { searchPhrase.isBlank() || it.title.contains(searchPhrase, ignoreCase = true) }

    Column(modifier = Modifier.fillMaxSize()) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Little Lemon logo",
                modifier = Modifier
                    .height(48.dp)
                    .align(Alignment.Center)
            )
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "Profile",
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.CenterEnd)
                    .clickable { navController.navigate(ProfileDestination.route) }
            )
        }

        // Hero section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF495E57))
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Little Lemon",
                        color = Color(0xFFF4CE14),
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = "Chicago", color = Color.White, fontSize = 24.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "We are a family-owned Mediterranean restaurant, focused on traditional recipes served with a modern twist",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Image(
                    painter = painterResource(id = R.drawable.heroimage),
                    contentDescription = "Hero image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = searchPhrase,
                onValueChange = { searchPhrase = it },
                placeholder = { Text("Enter search phrase") },
                leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Menu breakdown
        Text(
            text = "ORDER FOR DELIVERY!",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp)
        )
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            categories.forEach { category ->
                val selected = category == selectedCategory
                Button(
                    onClick = { selectedCategory = if (selected) null else category },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selected) Color(0xFFF4CE14) else Color(0xFFEDEFEE),
                        contentColor = Color.Black
                    ),
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text(category.replaceFirstChar { it.titlecase(Locale.getDefault()) })
                }
            }
        }

        MenuItems(filteredItems)
    }
}