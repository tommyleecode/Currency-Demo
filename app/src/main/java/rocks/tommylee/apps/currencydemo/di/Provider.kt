package rocks.tommylee.apps.currencydemo.di

import android.app.Application
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import rocks.tommylee.apps.currencydemo.database.AppDatabase
import rocks.tommylee.apps.currencydemo.database.dao.CurrencyDao
import rocks.tommylee.apps.currencydemo.utils.FileUtils

fun provideDatabase(application: Application): AppDatabase {
    return Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        "db"
    ).build()
}

fun provideCurrencyDao(db: AppDatabase): CurrencyDao {
    return db.currencyDao()
}

fun provideFileUtils(application: Application) = FileUtils(application)