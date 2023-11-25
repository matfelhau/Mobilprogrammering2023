
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import com.example.matapp.model.CreateRecipeViewModel
import com.example.matapp.utility.Utility
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class CreateRecipeScreen : ComponentActivity() {
    private val recipeId by lazy { FirebaseDatabase.getInstance().getReference("recipes").push().key.toString() }
    private val userId by lazy { FirebaseAuth.getInstance().currentUser?.uid }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val recipeViewModel: CreateRecipeViewModel by viewModels()
        recipeViewModel.initializeRecipe(userId, recipeId)

        setContent {
            val navController = rememberNavController()
            CreateRecipeLayout(
                navController = navController,
                recipeId = recipeId,
                userId = userId,
                recipeViewModel = recipeViewModel
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateRecipeLayout(
    navController: NavController,
    recipeId: String,
    userId: String?,
    recipeViewModel: CreateRecipeViewModel
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
    var checkboxGlutenState by remember { mutableStateOf(false) }
    var checkboxVeganState by remember { mutableStateOf(false) }
    var checkboxSoyState by remember { mutableStateOf(false) }
    var checkboxNutsState by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = recipeId) {
        title = ""
        cookTime = ""
        ingredient = ""
        quantity = ""
        measuringUnit = ""
        selectedSpiceLevel = ""
        selectedDifficulty = ""
        addedIngredients.clear()
        checkboxGlutenState = false
        checkboxVeganState = false
        checkboxSoyState = false
        checkboxNutsState = false
    }

    fun onCheckboxGlutenClicked() {
        checkboxGlutenState = !checkboxGlutenState
    }

    fun onCheckboxVeganClicked() {
        checkboxVeganState = !checkboxVeganState
    }

    fun onCheckboxSoyClicked() {
        checkboxSoyState = !checkboxSoyState
    }

    fun onCheckboxNutsClicked() {
        checkboxNutsState = !checkboxNutsState
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

            LazyColumn {
                items(recipeViewModel.addedIngredients) { ingredient ->
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
                          recipeViewModel.addIngredient(recipeId, ingredient, quantity, measuringUnit)
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
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                ExposedDropdownMenuBox(
                    expanded = isExpandedDifficulty,
                    onExpandedChange = { isExpandedDifficulty = it }
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
                        placeholder = { Text(text = "Choose difficulty level") }
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
            }

                var isExpandedSpice by remember { mutableStateOf(false) }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                ExposedDropdownMenuBox(
                    expanded = isExpandedSpice,
                    onExpandedChange = { isExpandedSpice = it }
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
                        placeholder = { Text(text = "Choose spice level") }
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
                    checked = checkboxGlutenState,
                    onCheckedChange = { onCheckboxGlutenClicked() },
                )
                Text("Gluten")
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Checkbox(
                    checked = checkboxVeganState,
                    onCheckedChange = { onCheckboxVeganClicked() },
                )
                Text("Vegan")
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Checkbox(
                    checked = checkboxSoyState,
                    onCheckedChange = { onCheckboxSoyClicked() },
                )
                Text("Soy")
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Checkbox(
                    checked = checkboxNutsState,
                    onCheckedChange = { onCheckboxNutsClicked() },
                )
                Text("Nuts")
            }
        }

        Button(
            onClick = {
                if (title.isEmpty()) Utility.showMessage(context, "Please enter a title.")
                if (cookTime.isEmpty()) Utility.showMessage(context, "Please enter a cook time.")
                if (selectedDifficulty.isEmpty()) Utility.showMessage(context, "Please choose a difficulty.")
                if (selectedSpiceLevel.isEmpty()) Utility.showMessage(context, "Please choose a spice level.")

                val allergens = mapOf(
                    "gluten" to checkboxGlutenState,
                    "nuts" to checkboxNutsState,
                    "soy" to checkboxSoyState,
                )

                recipeViewModel.createRecipeAndAddToDatabase(
                    recipeId,
                    userId,
                    title,
                    cookTime,
                    selectedDifficulty,
                    selectedSpiceLevel,
                    checkboxVeganState,
                    allergens
                )
            }
        ) {
            Text(
                text = "Create",
                color = Color.White,
                fontSize = 20.sp
            )
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