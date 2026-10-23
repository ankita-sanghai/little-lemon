package com.example.littlelemon.composables

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
fun Profile(navController: NavHostController) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("LittleLemon", Context.MODE_PRIVATE)
    val firstName = prefs.getString("firstName", "") ?: ""
    val lastName = prefs.getString("lastName", "") ?: ""
    val email = prefs.getString("email", "") ?: ""

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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 24.dp)
        ) {
            Text(
                text = "Profile information:",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(text = "First name", fontSize = 12.sp)
            Text(text = firstName, fontSize = 16.sp)

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Last name", fontSize = 12.sp)
            Text(text = lastName, fontSize = 16.sp)

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Email", fontSize = 12.sp)
            Text(text = email, fontSize = 16.sp)

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    prefs.edit().clear().apply()
                    navController.navigate(Onboarding.route) {
                        popUpTo(0)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF4CE14),
                    contentColor = Color.Black
                )
            ) {
                Text(text = "Log out")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    Profile(rememberNavController())
}