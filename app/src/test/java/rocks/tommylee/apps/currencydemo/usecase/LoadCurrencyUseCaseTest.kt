package rocks.tommylee.apps.currencydemo.usecase

import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runTest
import org.junit.Test
import rocks.tommylee.apps.currencydemo.BaseTest
import rocks.tommylee.apps.currencydemo.database.data.Currency
import rocks.tommylee.apps.currencydemo.domain.repository.CurrencyRepository
import rocks.tommylee.apps.currencydemo.domain.usecase.LoadCurrencyUseCase
import rocks.tommylee.apps.currencydemo.domain.usecase.UseCaseResult
import kotlin.test.assertEquals

class LoadCurrencyUseCaseTest : BaseTest() {

    private val currencyRepository: CurrencyRepository = mockk()
    private val loadCurrencyUseCase = LoadCurrencyUseCase(currencyRepository)

    private val currencies = listOf(
        Currency(id = "BTC", name = "Bitcoin", symbol = "BTC"),
        Currency(id = "ETH", name = "Ethereum", symbol = "ETH"),
        Currency(id = "XRP", name = "XRP", symbol = "XRP")
    )

    @Test
    fun `when load currency use case is invoked`() = runTest {
        coEvery { currencyRepository.all(any()) } returns currencies

        loadCurrencyUseCase(false).test {
            assertEquals(UseCaseResult.Loading, awaitItem())
            assertEquals(UseCaseResult.Success(currencies), awaitItem())
            awaitComplete()
        }

        coVerify(exactly = 1) { currencyRepository.all(false) }
    }
}