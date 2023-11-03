
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.matapp.R
import com.example.matapp.Utility


@Composable
fun TopNavBar(
    onHamburgerMenuClick: () -> Unit,
    onProfilePictureClick: () -> Unit,
    nameString: String

    ) {
    val context = LocalContext.current
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
                .clickable {
                    onProfilePictureClick()
                    Utility.showError(context = context, "Coming soon!")
                }
        )
    }
}


