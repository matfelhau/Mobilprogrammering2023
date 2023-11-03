import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.matapp.Utility
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilePictureLayout(navController: NavController) {
    val context = LocalContext.current
    var changeUsername by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    val currentUser = FirebaseAuth.getInstance().currentUser
    val userId by lazy { FirebaseAuth.getInstance().currentUser?.uid }
    val database = Firebase.database
    val userRef = database.reference.child("users").child(userId.toString()).child("username")

    LaunchedEffect(userId) {
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val usernameFromDatabase = snapshot.getValue(String::class.java)
                usernameFromDatabase?.let {
                    username = it
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Utility.showError(context = context, "Failed to read username")
            }
        }

        userRef.addValueEventListener(valueEventListener)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TopAppBar(
            title = { Text("Profile Picture") },
            navigationIcon = {
                IconButton(
                    onClick = {
                        navController.popBackStack()
                    },
                    content = {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                )
            },
            actions = {
                IconButton(
                    onClick = {
                        saveUsernameToDatabase(changeUsername, currentUser?.uid)
                        navController.navigate(Screen.ForYou.route)
                        Utility.showError(context = context, "Username saved!")
                    },
                    content = {
                        Icon(imageVector = Icons.Default.Done, "Save")
                    }
                )
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Hello, $username",
                fontSize = 25.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = changeUsername,
            onValueChange = {
                changeUsername = it
            },
            label = { Text(text = "Enter a username") },
            singleLine = true,
            textStyle = TextStyle.Default,
            keyboardOptions = KeyboardOptions.Default,
            keyboardActions = KeyboardActions.Default,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

private fun saveUsernameToDatabase(username: String, userId: String?) {
    if (userId != null) {
        val database = Firebase.database
        val userRef = database.reference.child("users").child(userId)
        userRef.child("username").setValue(username)
    }
}
