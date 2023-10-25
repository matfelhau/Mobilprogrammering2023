package com.example.matapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.matapp.ui.theme.MatappTheme

class SearchActivityCompose : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MatappTheme {
                SearchLayout()
            }
        }
    }
}

@Composable
fun SearchLayout() {

    Column(
        modifier = Modifier.fillMaxSize(),
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
                text = "Search",
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



        // BOTTOM NAV BAR ROW
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ){
            Image(
                painter = painterResource(R.drawable.foryou),
                contentDescription = "Profile picture",
                modifier = Modifier
                    .size(40.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(R.drawable.search),
                contentDescription = "Profile picture",
                modifier = Modifier
                    .size(40.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(R.drawable.saved),
                contentDescription = "Profile picture",
                modifier = Modifier
                    .size(40.dp)
                    .clickable {

                    }
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(R.drawable.settings),
                contentDescription = "Profile picture",
                modifier = Modifier
                    .size(40.dp)
            )
        }

    }
}


@Composable
fun SearchScreen() {
    SearchLayout()
}

@Preview
@Composable
fun SearchPreview() {
    MatappTheme {
        SearchScreen()
    }
}