package rocks.tommylee.apps.currencydemo.utils

import android.content.Context

class FileUtils(private val context: Context) {
    fun readStringFromAssetFile(filename: String): String {

        return try {
            val inputStream = context.resources.assets.open(filename)
            inputStream.readBytes().toString(Charsets.UTF_8)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }
}