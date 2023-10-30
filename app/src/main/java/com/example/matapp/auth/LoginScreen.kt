import android.content.ContentValues
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
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
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.matapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.database.FirebaseDatabase

class LoginScreen : ComponentActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        auth = FirebaseAuth.getInstance()
        super.onCreate(savedInstanceState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    val database = FirebaseDatabase.getInstance().getReference("users")
    val auth = FirebaseAuth.getInstance()


    val currentUser = auth.currentUser
    if (currentUser != null) {
        navController.navigate(Screen.ForYou.route)
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
            modifier = Modifier
                .fillMaxWidth()
            //.padding(16.dp)
        )

        Spacer(modifier =  Modifier.height(16.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
            //.padding(16.dp)
        )

        Spacer(modifier =  Modifier.height(16.dp))

        Row {
            //reset password
            Button(
                onClick = {
                    val emailText = email.text

                    if (emailText.isEmpty()) {
                        Toast.makeText(
                            context,
                            "Please enter an email address.",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        auth.sendPasswordResetEmail(emailText).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d(ContentValues.TAG, "Email sent successfully.")
                                Toast.makeText(
                                    context,
                                    "Password reset email sent!",
                                    Toast.LENGTH_SHORT,
                                ).show()
                            } else {
                                Log.e(ContentValues.TAG, "Error sending password reset email.", task.exception)
                                Toast.makeText(
                                    context,
                                    "Error sending password reset email.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
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

            //register
            Button(
                onClick = {
                    val emailText = email.text
                    val passwordText = password.text

                    if (emailText.isNotEmpty() && passwordText.isNotEmpty()) {
                        auth.createUserWithEmailAndPassword(emailText, passwordText)
                            .addOnCompleteListener() { task ->
                                if (task.isSuccessful) {
                                    Log.d(ContentValues.TAG, "createUserWithEmail:success")
                                    val user = auth.currentUser
                                    user?.let {
                                        val userId = it.uid
                                        val userEmail = it.email ?: ""
                                        val userData = mapOf("email" to userEmail)
                                        database.child(userId).setValue(userData)
                                    }
                                    user?.sendEmailVerification()?.addOnSuccessListener {
                                        Toast.makeText(
                                            context,
                                            "Account registered, please verify your email.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }?.addOnFailureListener {
                                        Toast.makeText(
                                            context,
                                            "Failed to send verification email",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                } else {
                                    when (task.exception) {
                                        is FirebaseAuthUserCollisionException -> {
                                            Toast.makeText(
                                                context,
                                                "This email is already in use",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }

                                        else -> {
                                            Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)
                                            Toast.makeText(
                                                context,
                                                "Authentication failed.",
                                                Toast.LENGTH_SHORT,
                                            ).show()
                                        }
                                    }
                                }
                            }
                    } else {
                        Toast.makeText(
                            context,
                            "Please enter email and password",
                            Toast.LENGTH_SHORT
                        ).show()
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

        //login
        Button(
            onClick = {
                val emailText = email.text
                val passwordText = password.text

                if (emailText.isNotEmpty() && passwordText.isNotEmpty()) {
                    auth.signInWithEmailAndPassword(emailText, passwordText)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val user = auth.currentUser
                                if (user?.isEmailVerified == true) {
                                    Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show()
                                    navController.navigate(Screen.ForYou.route)
                                }
                                else {
                                    Toast.makeText(context, "Please verify your email before logging in.", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                Toast.makeText(context, "Fail!: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                } else {
                    Toast.makeText(context, "Please enter email and password.", Toast.LENGTH_SHORT).show()
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
