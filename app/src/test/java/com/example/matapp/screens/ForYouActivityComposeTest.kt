package com.example.matapp.screens

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.matapp.model.ForYouViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ForYouActivityComposeTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ForYouActivityCompose>()

    @Test
    fun testIfTitleIsForYou() {
        composeTestRule.setContent {
            ForYouLayout(navController = rememberNavController(), viewModel = ForYouViewModel())
        }

        composeTestRule.onNodeWithText("For You").assertExists()
    }
}
