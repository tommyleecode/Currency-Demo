package rocks.tommylee.apps.currencydemo.usecase

import android.app.Application
import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import rocks.tommylee.apps.currencydemo.R
import rocks.tommylee.apps.currencydemo.database.data.Currency
import rocks.tommylee.apps.currencydemo.domain.repository.CurrencyRepository
import rocks.tommylee.apps.currencydemo.domain.usecase.InsertCurrencyUseCase
import rocks.tommylee.apps.currencydemo.domain.usecase.UseCaseResult
import kotlin.test.assertEquals

class InsertCurrencyUseCaseTest {

    private val context: Application = mockk()
    private val currencyRepository: CurrencyRepository = mockk()
    private val insertCurrencyUseCase = InsertCurrencyUseCase(context, currencyRepository)

    private val currencies = listOf(
        Currency(id = "BTC", name = "Bitcoin", symbol = "BTC"),
        Currency(id = "ETH", name = "Ethereum", symbol = "ETH"),
        Currency(id = "XRP", name = "XRP", symbol = "XRP")
    )

    @ExperimentalCoroutinesApi
    @Test
    fun `insert a list of currencies`() = runTest {
        val successMsg = "Data inserted successfully"

        coEvery { currencyRepository.insert(any()) } returns Unit
        coEvery { context.getString(R.string.msg_data_inserted_successfully) } returns successMsg

        insertCurrencyUseCase(currencies).test {
            assertEquals(UseCaseResult.Loading, awaitItem())
            assertEquals(UseCaseResult.Success(successMsg), awaitItem())
            awaitComplete()
        }

        coVerify(exactly = 1) { currencyRepository.insert(currencies) }
    }
}