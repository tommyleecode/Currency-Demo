package rocks.tommylee.apps.currencydemo

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import rocks.tommylee.apps.currencydemo.database.AppDatabase
import rocks.tommylee.apps.currencydemo.database.dao.CurrencyDao
import rocks.tommylee.apps.currencydemo.database.data.Currency

@RunWith(AndroidJUnit4::class)
class RoomCurrencyTest {
    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext

    private lateinit var currencyDao: CurrencyDao
    private lateinit var appDatabase: AppDatabase

    private val currencies = listOf(
        Currency(id = "BTC", name = "Bitcoin", symbol = "BTC"),
        Currency(id = "ETH", name = "Ethereum", symbol = "ETH"),
        Currency(id = "XRP", name = "XRP", symbol = "XRP")
    )

    @Before
    fun setup() {
        appDatabase = Room.inMemoryDatabaseBuilder(appContext, AppDatabase::class.java).build()
        currencyDao = appDatabase.currencyDao()
    }

    @After
    fun tearDown() {
        appDatabase.close()
        appDatabase.clearAllTables()
    }

    @Test
    fun insertAListOfCurrenciesSuccessfully() = runBlocking {
        currencies.forEach {
            currencyDao.insert(it)
        }

        val result = currencyDao.all()

        Assert.assertEquals(currencies, result)
    }
}