package com.example.littlelemon.composables

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.littlelemon.R

@Composable
fun Onboarding(navController: NavHostController) {
    val context = LocalContext.current
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Little Lemon logo",
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .padding(vertical = 16.dp)
                .height(80.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(Color(0xFF495E57)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Let's get to know you",
                color = Color.White,
                fontSize = 24.sp
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Personal information",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(text = "First name", fontSize = 12.sp)
            TextField(
                value = firstName,
                onValueChange = { firstName = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Last name", fontSize = 12.sp)
            TextField(
                value = lastName,
                onValueChange = { lastName = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Email", fontSize = 12.sp)
            TextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(text = message, modifier = Modifier.padding(bottom = 8.dp))

            Button(
                onClick = {
                    if (firstName.isBlank() || lastName.isBlank() || email.isBlank()) {
                        message = "Registration unsuccessful. Please enter all data."
                    } else {
                        context.getSharedPreferences("LittleLemon", Context.MODE_PRIVATE)
                            .edit()
                            .putString("firstName", firstName)
                            .putString("lastName", lastName)
                            .putString("email", email)
                            .apply()
                        message = "Registration successful!"
                        navController.navigate(Home.route) {
                            popUpTo(Onboarding.route) { inclusive = true }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF4CE14),
                    contentColor = Color.Black
                )
            ) {
                Text(text = "Register")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingPreview() {
    Onboarding(rememberNavController())
}