import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.matapp.R


@Composable
fun TopNavBar(
    onHamburgerMenuClick: () -> Unit,
    onProfilePictureClick: () -> Unit,
    nameString: String

    ) {
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
                .clickable { onHamburgerMenuClick() }
        )
        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = nameString,
            fontSize = 30.sp
        )

        Spacer(modifier = Modifier.weight(1f))

        Image(
            painter = painterResource(R.drawable.usericon),
            contentDescription = "Profile picture",
            modifier = Modifier
                .size(40.dp)
                .clickable { onProfilePictureClick() }
        )
    }
}


