
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

private var dataAdded = false
class CreateRecipeScreen : ComponentActivity() {
    private lateinit var recipeId: String
    private val database by lazy { FirebaseDatabase.getInstance().getReference("recipes") }
    private val userId by lazy { FirebaseAuth.getInstance().currentUser?.uid }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        recipeId = database.push().key.toString()
        initializeRecipe()

        setContent {
            val navController = rememberNavController()
            CreateRecipeLayout(
                navController = navController,
                recipeId = recipeId,
                userId = userId,
                database = database
                )
        }
    }
    private fun initializeRecipe() {
        val initialRecipeData = mapOf(
            "userId" to userId,
            "title" to "",
            "cookTime" to "",
            "difficulty" to "",
            "spiceLevel" to ""
        )
        database.child(recipeId).setValue(initialRecipeData)
    }

    override fun onBackPressed() {
        if (!dataAdded) {
            database.child(recipeId).removeValue()
        }
        super.onBackPressed()
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateRecipeLayout(
    navController: NavController,
    recipeId: String,
    userId: String?,
    database: DatabaseReference
) {
    val context = LocalContext.current
    var title by remember { mutableStateOf("") }
    var cookTime by remember { mutableStateOf("") }
    var ingredient by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var measuringUnit by remember { mutableStateOf("") }
    var selectedSpiceLevel by remember { mutableStateOf("") }
    var selectedDifficulty by remember { mutableStateOf("") }
    val addedIngredients = remember { mutableStateListOf<String>() }

    var checkbox1State by remember { mutableStateOf(false) }
    var checkbox2State by remember { mutableStateOf(false) }
    var checkbox3State by remember { mutableStateOf(false) }
    var checkbox4State by remember { mutableStateOf(false) }

    fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

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
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopNavBar(
            nameString = "Create Recipe",
            onHamburgerMenuClick = {
                navController.navigate(Screen.Create.route)
            },
            onProfilePictureClick = {
                navController.navigate(Screen.Profile.route)
            },
        )

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.weight(1f)
                )

                OutlinedTextField(
                    value = cookTime,
                    onValueChange = { cookTime = it },
                    label = { Text("Cook time") },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedTextField(
                    value = ingredient,
                    onValueChange = { ingredient = it },
                    label = { Text("Ingredient") },
                    modifier = Modifier.weight(1f)
                )

                OutlinedTextField(
                    value = quantity,
                    onValueChange = { quantity = it },
                    label = { Text("Quantity") },
                    modifier = Modifier.weight(1f)
                )

                OutlinedTextField(
                    value = measuringUnit,
                    onValueChange = { measuringUnit = it },
                    label = { Text("M-Unit") },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Display added ingredients
            LazyColumn {
                items(addedIngredients.toList()) { ingredient ->
                    Text(
                        text = ingredient,
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ADD INGREDIENTS
            Button(
                onClick = {

                    if (ingredient.isEmpty() || quantity.isEmpty() || measuringUnit.isEmpty()) {
                        showError("Please fill out all fields.")
                    } else {
                        val ingredientData = mapOf(
                            "name" to ingredient,
                            "quantity" to quantity,
                            "unit" to measuringUnit
                        )

                        recipeId?.let {
                            database.child(it).child("ingredients").push().setValue(ingredientData)
                                .addOnSuccessListener {
                                    addedIngredients.add("$ingredient $quantity $measuringUnit")
                                    dataAdded = true

                                    ingredient = ""
                                    quantity = ""
                                    measuringUnit = ""


                                    Toast.makeText(
                                        context,
                                        "Data added to Firebase Database",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Add ingredient",
                    color = Color.White,
                    fontSize = 20.sp
                )
            }
        }
        var isExpandedDifficulty by remember { mutableStateOf(false) }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ExposedDropdownMenuBox(
                expanded = isExpandedDifficulty,
                onExpandedChange = {isExpandedDifficulty = it}
            ) {
                TextField(
                    value = selectedDifficulty,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpandedDifficulty)
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(),
                    modifier = Modifier.menuAnchor(),
                    placeholder = {Text(text="Choose difficulty level")}
                )

                ExposedDropdownMenu(expanded = isExpandedDifficulty,
                    onDismissRequest = { isExpandedDifficulty = false }
                ) {
                    DropdownMenuItem(
                        text = {
                            Text(text = "Easy")
                        },
                        onClick = {
                            selectedDifficulty = "Easy"
                            isExpandedDifficulty = false
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Text(text = "Intermediate")
                        },
                        onClick = {
                            selectedDifficulty = "Intermediate"
                            isExpandedDifficulty = false
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Text(text = "Advanced")
                        },
                        onClick = {
                            selectedDifficulty = "Advanced"
                            isExpandedDifficulty = false
                        }
                    )
                }
            }

            var isExpandedSpice by remember { mutableStateOf(false) }

            ExposedDropdownMenuBox(
                expanded = isExpandedSpice,
                onExpandedChange = {isExpandedSpice = it}
            ) {
                TextField(
                    value = selectedSpiceLevel,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpandedSpice)
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(),
                    modifier = Modifier.menuAnchor(),
                    placeholder = {Text(text="Choose spice level")}
                )

                ExposedDropdownMenu(expanded = isExpandedSpice,
                    onDismissRequest = { isExpandedSpice = false }
                ) {
                    DropdownMenuItem(
                        text = {
                            Text(text = "Mild")
                        },
                        onClick = {
                            selectedSpiceLevel = "Mild"
                            isExpandedSpice = false
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Text(text = "Intermediate")
                        },
                        onClick = {
                            selectedSpiceLevel = "Intermediate"
                            isExpandedSpice = false
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Text(text = "Advanced")
                        },
                        onClick = {
                            selectedSpiceLevel = "Advanced"
                            isExpandedSpice = false
                        }
                    )
                }
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

        Button(
            onClick = {
                val recipeData = mapOf(
                    "userId" to userId,
                    "title" to title,
                    "cookTime" to cookTime,
                    "difficulty" to selectedDifficulty,
                    "spiceLevel" to selectedSpiceLevel,
                )

                database.child(recipeId!!).updateChildren(recipeData).addOnSuccessListener {
                    Log.d("FirebaseDebug", "Data written successfully!")
                    dataAdded = true
                }.addOnFailureListener { exception ->
                    Log.e("FirebaseDebug", "Error writing data: ", exception)
                }
            }
        ) {
            Text(
                text = "Create",
                color = Color.White,
                fontSize = 20.sp
            )
        }

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