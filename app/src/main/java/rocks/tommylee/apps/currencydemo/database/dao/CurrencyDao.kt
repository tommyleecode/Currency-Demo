package rocks.tommylee.apps.currencydemo.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import rocks.tommylee.apps.currencydemo.database.data.Currency

@Dao
interface CurrencyDao {
    @Query("SELECT * FROM Currency ORDER BY name ASC")
    fun all(): List<Currency>

    @Insert
    fun insert(currency: Currency)
}