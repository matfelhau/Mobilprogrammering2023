package com.example.matapp.screens

import BottomNavBar
import Screen
import TopNavBar
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.matapp.MainActivity
import com.example.matapp.R
import com.example.matapp.ui.theme.MatappTheme
import com.example.matapp.utility.DatabaseUtils
import com.example.matapp.utility.Utility
import com.google.firebase.auth.FirebaseAuth

class SettingsCompose : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainActivity.createNotificationChannel(this)

        setContent {
            val navController = rememberNavController()
            SettingsLayout(navController = navController)
        }
    }
}

@Composable
fun SettingsLayout(navController: NavController) {
    val context = LocalContext.current
    var isVegan by remember { mutableStateOf(false) }
    var notificationOn by remember { mutableStateOf(false) }
    val auth = FirebaseAuth.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        TopNavBar(
            nameString = "Settings",
            onHamburgerMenuClick = {
                navController.navigate(Screen.Create.route)
            },
            onProfilePictureClick = {
                navController.navigate(Screen.Profile.route)
            }
        )

        Spacer(modifier = Modifier.weight(0.1f))
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = stringResource(id = R.string.vegan_mode),
                    modifier = Modifier.padding(start = 33.dp, bottom = 8.dp)
                )
                Switch(
                    checked = isVegan,
                    onCheckedChange = {
                        isVegan = it
                    },
                    modifier = Modifier
                        .width(147.dp)
                        .height(24.dp)
                )
            }
        }
        Spacer(modifier = Modifier.weight(0.03f))
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = stringResource(id = R.string.notifications),
                    modifier = Modifier.padding(start = 30.dp)
                )
                Switch(
                    checked = notificationOn,
                    onCheckedChange = {
                        notificationOn = it
                    },
                    modifier = Modifier
                        .width(147.dp)
                        .height(24.dp)
                )
            }
        }
        Spacer(modifier = Modifier.weight(0.2f))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                          auth.sendPasswordResetEmail(auth.currentUser?.email ?: "").addOnCompleteListener {
                              if (it.isSuccessful) {
                                  Utility.showLogcatDebug(Utility.RESET_MAIL_MESSAGE)
                                  Utility.showMessage(context, Utility.RESET_MAIL_MESSAGE)
                              } else {
                                  Utility.showLogcatError(Utility.ERROR_SENDING_RESET_MAIL)
                                  it.exception
                                  Utility.showMessage(context, Utility.ERROR_SENDING_RESET_MAIL)
                              }
                          }
                },
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(text = stringResource(id = R.string.new_password))
            }

            Button(
                onClick = {
                    Utility.showMessage(context, "Coming soon!")
                },
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(text = stringResource(id = R.string.change_email))
            }

            Button(
                onClick = {
                    val currentUser = auth.currentUser
                    if (currentUser != null) {
                        auth.signOut()
                        navController.navigate(Screen.Login.route)
                    }
                },
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(text = stringResource(id = R.string.log_out))
            }
            
            Text(text = "Admin panel for sensor/debug")
            Text(text = "This button deletes empty recipes")

            Button(
                onClick = {
                    DatabaseUtils.deleteEmptyRecipes()
                }) {
                Text(text = "Delete all recipes")
            }

            Button(
                onClick = {
                    Utility.showLogcatDebug("Button clicked")
                    MainActivity.createNotificationChannel(context)
                    MainActivity.showNotification(context)
                }
            ) {
                Text(text = "Notification test")
            }

        }
        Spacer(modifier = Modifier.weight(1f))
        BottomNavBar(
            onForYouClick = {
                navController.navigate(Screen.ForYou.route)
            },
            onSearchClick = {
                navController.navigate(Screen.Search.route)
            },
            onSavedClick = {
                navController.navigate(Screen.Saved.route)
            },
            onSettingsClick = {
                navController.navigate(Screen.Settings.route)
            }
        )
    }
}
@Composable
fun CreateSettingsScreen(navController: NavController) {
    SettingsLayout(navController)
}

@Preview
@Composable
fun CreateSettingsPreview() {
    val navController: NavController = rememberNavController()

    MatappTheme {
        CreateSettingsScreen(navController = navController)
    }
}