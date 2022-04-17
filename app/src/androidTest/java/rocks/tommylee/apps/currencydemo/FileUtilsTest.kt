package rocks.tommylee.apps.currencydemo

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import rocks.tommylee.apps.currencydemo.utils.FileUtils

@RunWith(AndroidJUnit4::class)
class FileUtilsTest {

    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    private val fileUtils = FileUtils(appContext.applicationContext)
    private val filename = "sample.json"

    @Test
    fun loadFileFromAssetFolder_success() {

        val fileReadOut = fileUtils.readStringFromAssetFile(filename)

        Assert.assertNotNull(fileReadOut)
        Assert.assertTrue(fileReadOut.isNotEmpty())
    }

    @Test
    fun loadFileFromAssetFolder_failed() {
        val fileReadOut = fileUtils.readStringFromAssetFile("dummy.json")

        Assert.assertTrue(fileReadOut.isEmpty())
    }
}