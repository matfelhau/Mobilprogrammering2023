package com.example.matapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.matapp.ui.theme.MatappTheme


class CreateRecipeActivityCompose : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MatappTheme {
                CreateRecipeLayout()
            }
        }
    }
}

@Composable
fun CreateRecipeLayout() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {

        // TOP NAV BAR ROW
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.menu),
                contentDescription = "Hamburger menu",
                modifier = Modifier
                    .size(40.dp)
            )
            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Create Recipe",
                fontSize = 30.sp
            )

            Spacer(modifier = Modifier.weight(1f))

            Image(
                painter = painterResource(R.drawable.usericon),
                contentDescription = "Profile picture",
                modifier = Modifier
                    .size(40.dp)
            )
        }

        Column( modifier = Modifier
            .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            ) {

            Spacer(modifier = Modifier.height(16.dp))

            // ADD INGREDIENT BUTTON
            Button(
                onClick = {

                },
                modifier = Modifier
                    //.background(Color.Gray)
            ) {
                Text(
                    text = "Add Ingredient",
                    color = Color.White, // Set the text color
                    fontSize = 20.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // CREATE RECIPE BUTTON
            Button(
                onClick = {

                },
                modifier = Modifier
                    //.background(Color.Gray)
            ) {
                Text(
                    text = "Create",
                    color = Color.White, // Set the text color
                    fontSize = 20.sp
                )
            }
        }


        // BOTTOM NAV BAR ROW
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ){
            Image(
                painter = painterResource(R.drawable.foryou),
                contentDescription = "For You Icon",
                modifier = Modifier
                    .size(40.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(R.drawable.search),
                contentDescription = "Search Icon",
                modifier = Modifier
                    .size(40.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(R.drawable.saved),
                contentDescription = "Saved Icon",
                modifier = Modifier
                    .size(40.dp)
                    .clickable {

                    }
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(R.drawable.settings),
                contentDescription = "Settings Icon",
                modifier = Modifier
                    .size(40.dp)
            )
        }

    }
}


@Composable
fun CreateRecipeScreen() {
    CreateRecipeLayout()
}

@Preview
@Composable
fun CreateRecipePreview() {
    MatappTheme {
        CreateRecipeScreen()
    }
}