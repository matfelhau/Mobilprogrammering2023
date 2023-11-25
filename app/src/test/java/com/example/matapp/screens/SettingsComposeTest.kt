import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MyUITest {

    @Test
    fun testUIInteractions() {
        assertEquals(4, 2+2)
    }
}