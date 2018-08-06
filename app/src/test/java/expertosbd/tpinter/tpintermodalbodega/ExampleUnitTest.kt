package expertosbd.tpinter.tpintermodalbodega

import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val format = SimpleDateFormat("dd/MM/YYYY,HH:mm")
        val date = format.format(Calendar.getInstance().time)
        assertEquals("12/07/2018,15:54", date)
    }
}
