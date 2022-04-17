package rocks.tommylee.apps.currencydemo.repository

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import rocks.tommylee.apps.currencydemo.BaseTest
import rocks.tommylee.apps.currencydemo.database.dao.CurrencyDao
import rocks.tommylee.apps.currencydemo.database.data.Currency
import rocks.tommylee.apps.currencydemo.domain.repository.CurrencyRepository
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class CurrencyRepositoryTest : BaseTest() {

    private val currencyDao: CurrencyDao = mockk()
    private val currencyRepository = CurrencyRepository(currencyDao)

    private val currencies = listOf(
        Currency(id = "BTC", name = "Bitcoin", symbol = "BTC"),
        Currency(id = "ETH", name = "Ethereum", symbol = "ETH"),
        Currency(id = "XRP", name = "XRP", symbol = "XRP")
    )

    @Test
    fun `getting all results in descending order`() = runTest {
        coEvery { currencyDao.all() } returns currencies

        val result = currencyRepository.all(true)

        coVerify(exactly = 1) { currencyDao.all() }

        assertNotNull(result)
        assertEquals(expected = "XRP", actual = currencies[2].id)
    }

    @Test
    fun `getting all results in ascending order`() = runTest {
        coEvery { currencyDao.all() } returns currencies

        val result = currencyRepository.all(false)

        coVerify(exactly = 1) { currencyDao.all() }

        assertNotNull(result)
        assertEquals(expected = "BTC", actual = currencies[0].id)
    }

    @Test
    fun `insert all from list`() = runTest {
        coEvery { currencyDao.insert(any()) } returns Unit

        currencyRepository.insert(currencies)

        coVerify(exactly = 3) { currencyDao.insert(any()) }
    }
}