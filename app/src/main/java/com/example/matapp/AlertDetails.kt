
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.matapp.screens.ForYouActivityCompose

class AlertDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val targetIntent = Intent(this, ForYouActivityCompose::class.java)
        startActivity(targetIntent)
        finish()
    }
}
