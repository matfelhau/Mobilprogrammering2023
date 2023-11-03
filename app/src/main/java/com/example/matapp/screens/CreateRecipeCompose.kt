package com.example.matapp.screens

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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.matapp.R
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

    var checkbox1State by remember { mutableStateOf(false) }
    var checkbox2State by remember { mutableStateOf(false) }
    var checkbox3State by remember { mutableStateOf(false) }
    var checkbox4State by remember { mutableStateOf(false) }

    fun onCheckbox1Clicked() {
        checkbox1State = !checkbox1State
    }

    fun onCheckbox2Clicked() {
        checkbox2State = !checkbox2State
    }

    fun onCheckbox3Clicked() {
        checkbox3State = !checkbox3State
    }

    fun onCheckbox4Clicked() {
        checkbox4State = !checkbox4State
    }

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

            Button(
                onClick = {

                },
                modifier = Modifier
            ) {
                Text(
                    text = "Add Ingredient",
                    color = Color.White,
                    fontSize = 20.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {

                },
                modifier = Modifier
            ) {
                Text(
                    text = "Create",
                    color = Color.White,
                    fontSize = 20.sp
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Checkbox(
                    checked = checkbox1State,
                    onCheckedChange = { checked -> onCheckbox1Clicked() },
                )
                Text("Gluten")
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Checkbox(
                    checked = checkbox2State,
                    onCheckedChange = { checked -> onCheckbox2Clicked() },
                )
                Text("Vegan")
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Checkbox(
                    checked = checkbox3State,
                    onCheckedChange = { checked -> onCheckbox3Clicked() },
                )
                Text("Soy")
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Checkbox(
                    checked = checkbox4State,
                    onCheckedChange = { checked -> onCheckbox4Clicked() },
                )
                Text("Nuts")
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