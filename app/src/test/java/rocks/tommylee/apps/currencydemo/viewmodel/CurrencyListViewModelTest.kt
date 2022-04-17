package rocks.tommylee.apps.currencydemo.viewmodel

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import rocks.tommylee.apps.currencydemo.BaseTest
import rocks.tommylee.apps.currencydemo.TestDispatcher
import rocks.tommylee.apps.currencydemo.database.data.Currency
import rocks.tommylee.apps.currencydemo.domain.usecase.LoadCurrencyUseCase
import rocks.tommylee.apps.currencydemo.domain.usecase.UseCaseResult
import rocks.tommylee.apps.currencydemo.ui.currency.CurrencyListViewModel
import kotlin.test.assertEquals

class CurrencyListViewModelTest : BaseTest() {

    private lateinit var viewModel: CurrencyListViewModel
    private lateinit var testDispatchers: TestDispatcher

    private val loadCurrencyUseCase: LoadCurrencyUseCase = mockk(relaxed = true)

    private val currencies = listOf(
        Currency(id = "BTC", name = "Bitcoin", symbol = "BTC"),
        Currency(id = "ETH", name = "Ethereum", symbol = "ETH"),
        Currency(id = "XRP", name = "XRP", symbol = "XRP")
    )

    @Before
    fun setup() {
        testDispatchers = TestDispatcher()
        viewModel = CurrencyListViewModel(
            dispatcher = testDispatchers,
            loadCurrencyUseCase = loadCurrencyUseCase
        )
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `when load() method is called, should load data`() = runTest {
        coEvery { loadCurrencyUseCase(any()) } returns flowOf(UseCaseResult.Success(currencies))
        viewModel.load(false)

        assertEquals(currencies, viewModel.data.value)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `when sort() method is called, should have data in ascending order`() {
        // Prep initial data
        coEvery { loadCurrencyUseCase(any()) } returns flowOf(UseCaseResult.Success(currencies))
        viewModel.load(false)

        // Sort
        viewModel.sort()

        assertEquals(currencies, viewModel.data.value)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `when sort() method is toggled, should have data in descending order`() {
        val sortedDecResult = currencies.sortedByDescending { it.name }

        coEvery { loadCurrencyUseCase(any()) } returns flowOf(UseCaseResult.Success(sortedDecResult))
        viewModel.load(true)
        viewModel.sort()

        assertEquals(sortedDecResult, viewModel.data.value)
    }
}