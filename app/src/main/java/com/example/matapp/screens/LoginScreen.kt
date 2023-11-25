
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.matapp.R
import com.example.matapp.model.LoginViewModel
import com.example.matapp.utility.Utility
import com.google.firebase.auth.FirebaseAuth


class LoginScreen : ComponentActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        auth = FirebaseAuth.getInstance()
        super.onCreate(savedInstanceState)
        val loginViewModel: LoginViewModel by viewModels()

        setContent {
            val navController = rememberNavController()
            LoginScreen(
                navController = navController,
                loginViewModel = loginViewModel
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController, loginViewModel: LoginViewModel) {
    val context = LocalContext.current
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser

    val uiState by loginViewModel.uiState.collectAsState()

    when (uiState) {
        is LoginViewModel.UiState.Success -> {
            navController.navigate(Screen.ForYou.route)
            loginViewModel.resetUiState()
        }
        is LoginViewModel.UiState.Error -> {
            Utility.showMessage(context, (uiState as LoginViewModel.UiState.Error).message)
            loginViewModel.resetUiState()
        }
        is LoginViewModel.UiState.RegistrationSuccess -> {
            Utility.showMessage(context, Utility.VERIFY_EMAIL)
        }
        LoginViewModel.UiState.Loading -> {

        }
        LoginViewModel.UiState.Idle -> {

        }
    }

    Column (
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Box {
            Image(painter = painterResource(
                id = R.drawable.ic_launcher_background),
                contentDescription = "logo"
            )
            Image(painter = painterResource(
                id = R.drawable.ic_launcher_foreground),
                contentDescription = "logo")
        }


        Text(
            text = "Matapp",
            fontSize = 30.sp
        )

        Spacer(modifier =  Modifier.height(16.dp))

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Email") },
            singleLine = true,
            textStyle = TextStyle.Default,
            keyboardOptions = KeyboardOptions.Default,
            keyboardActions = KeyboardActions.Default,
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier =  Modifier.height(16.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Password") },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            textStyle = TextStyle.Default,
            keyboardOptions = KeyboardOptions.Default,
            keyboardActions = KeyboardActions.Default,
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier =  Modifier.height(16.dp))

        Row {
            // RESET PASSWORD
            Button(
                onClick = {
                    val emailText = email.text

                    if (emailText.isEmpty()) {
                        Utility.showMessage(context, Utility.ENTER_EMAIL)
                    } else if (currentUser?.isEmailVerified == false) {
                        Utility.showMessage(context, Utility.VERIFY_EMAIL)
                    } else {
                        loginViewModel.resetPassword(emailText)
                        Utility.showMessage(context, Utility.CHECK_EMAIL)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.5f),
                shape = MaterialTheme.shapes.small
            ) {
                Text(
                    text="Reset password",
                    fontSize = 19.sp,
                )
            }

            Spacer(modifier =  Modifier.width(16.dp))

            // REGISTER USER
            Button(
                onClick = {
                    val emailText = email.text
                    val passwordText = password.text

                    if (emailText.isNotEmpty() && passwordText.isNotEmpty()) {
                        loginViewModel.registerUser(emailText, passwordText)
                        Utility.showMessage(context, Utility.USER_FEEDBACK)
                    } else {
                        Utility.showMessage(context, Utility.ENTER_EMAIL_AND_PASSWORD)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = MaterialTheme.shapes.small
            ) {
                Text(
                    text="Register",
                    fontSize = 19.sp,
                )
            }
        }

        Spacer(modifier =  Modifier.height(16.dp))

        // LOGIN
        Button(
            onClick = {
                val emailText = email.text
                val passwordText = password.text

                if (emailText.isNotEmpty() && passwordText.isNotEmpty()) {
                    loginViewModel.logIn(emailText, passwordText)
                }
                else {
                    Utility.showMessage(context, Utility.INPUT_FEEDBACK)
                }
            },
            modifier = Modifier
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.small
        ) {
            Text(
                text="Login",
                fontSize = 20.sp,
            )
        }
    }
}
