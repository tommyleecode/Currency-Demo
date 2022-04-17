package rocks.tommylee.apps.currencydemo.database.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
@JsonClass(generateAdapter = true)
data class Currency(
    @PrimaryKey val id: String,
    val name: String,
    val symbol
    : String
) : Parcelable