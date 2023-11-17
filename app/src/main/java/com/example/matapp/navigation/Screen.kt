sealed class Screen(val route: String) {
    data object Login : Screen("login")
    data object ForYou : Screen("foryou")
    data object Search : Screen("search")
    data object Saved : Screen("saved")
    data object Settings : Screen("settings")
    data object Create : Screen("create")
    data object Profile : Screen("profile")
    data object Recipe : Screen("recipe")

}
