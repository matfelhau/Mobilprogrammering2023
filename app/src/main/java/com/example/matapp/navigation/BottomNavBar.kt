import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.matapp.R

@Composable
fun BottomNavBar(
    onForYouClick: () -> Unit,
    onSearchClick: () -> Unit,
    onSavedClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    Row(
        modifier = Modifier.run {
            fillMaxWidth()
                .padding(16.dp)
        }
    ) {
        Image(
            painter = painterResource(R.drawable.foryou),
            contentDescription = "For You Icon",
            modifier = Modifier
                .size(40.dp)
                .clickable { onForYouClick() }
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(R.drawable.search),
            contentDescription = "Search Icon",
            modifier = Modifier
                .size(40.dp)
                .clickable { onSearchClick() }
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(R.drawable.saved),
            contentDescription = "Saved Icon",
            modifier = Modifier
                .size(40.dp)
                .clickable { onSavedClick() }
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(R.drawable.settings),
            contentDescription = "Settings Icon",
            modifier = Modifier
                .size(40.dp)
                .clickable { onSettingsClick() }
        )
    }
}


