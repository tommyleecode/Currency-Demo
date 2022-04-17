package rocks.tommylee.apps.currencydemo.database

import androidx.room.Database
import androidx.room.RoomDatabase
import rocks.tommylee.apps.currencydemo.database.dao.CurrencyDao
import rocks.tommylee.apps.currencydemo.database.data.Currency

@Database(
    entities = [Currency::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
}