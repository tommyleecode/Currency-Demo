package rocks.tommylee.apps.currencydemo.viewmodel

import android.app.Application
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Test
import rocks.tommylee.apps.currencydemo.BaseTest
import rocks.tommylee.apps.currencydemo.R
import rocks.tommylee.apps.currencydemo.TestDispatcher
import rocks.tommylee.apps.currencydemo.database.data.Currency
import rocks.tommylee.apps.currencydemo.domain.usecase.InsertCurrencyUseCase
import rocks.tommylee.apps.currencydemo.domain.usecase.LoadCurrencyUseCase
import rocks.tommylee.apps.currencydemo.domain.usecase.LoadFakeDataUseCase
import rocks.tommylee.apps.currencydemo.domain.usecase.UseCaseResult
import rocks.tommylee.apps.currencydemo.ui.main.MainViewModel
import rocks.tommylee.apps.currencydemo.utils.FileUtils
import kotlin.test.assertEquals

class MainViewModelTest : BaseTest() {
    private lateinit var viewModel: MainViewModel
    private lateinit var testDispatchers: TestDispatcher

    private val loadCurrencyUseCase: LoadCurrencyUseCase = mockk(relaxed = true)
    private val loadFakeDataUseCase: LoadFakeDataUseCase = mockk(relaxed = true)
    private val insertCurrencyUseCase: InsertCurrencyUseCase = mockk(relaxed = true)
    private val context: Application = mockk(relaxed = true)

    private val currencies = listOf(
        Currency(id = "BTC", name = "Bitcoin", symbol = "BTC"),
        Currency(id = "ETH", name = "Ethereum", symbol = "ETH"),
        Currency(id = "XRP", name = "XRP", symbol = "XRP")
    )

    @Before
    fun setup() {
        testDispatchers = TestDispatcher()
        viewModel = MainViewModel(
            dispatcher = testDispatchers,
            context = context,
            loadCurrencyUseCase = loadCurrencyUseCase,
            loadFakeDataUseCase = loadFakeDataUseCase,
            insertCurrencyUseCase = insertCurrencyUseCase
        )
    }

    @Test
    fun `given the data is already inserted, return message of it`() {
        val resultMessageInserted = "Data already inserted"

        coEvery { context.getString(R.string.toast_data_already_inserted) } returns resultMessageInserted
        coEvery { loadCurrencyUseCase() } returns flowOf(UseCaseResult.Success(currencies))

        viewModel.check()

        assertEquals(resultMessageInserted, viewModel.message.value)
    }

    @Test
    fun `given the data is not inserted, load data from file successfully`() {
        val resultMsgSuccess = "Data inserted successfully"

        coEvery { context.getString(R.string.msg_data_inserted_successfully) } returns resultMsgSuccess
        coEvery { loadCurrencyUseCase() } returns flowOf(UseCaseResult.Success(emptyList()))
        coEvery { loadFakeDataUseCase() } returns flowOf(UseCaseResult.Success(currencies))
        coEvery { insertCurrencyUseCase(currencies) } returns flowOf(
            UseCaseResult.Success(
                resultMsgSuccess
            )
        )

        viewModel.check()

        assertEquals(resultMsgSuccess, viewModel.message.value)
    }

    @Test
    fun `given the data is not inserted, load data from file failed`() {
        val resultMsgErrorLoadingFake = "Loading went wrong"
        val fileUtils: FileUtils = mockk(relaxed = true)

        coEvery { context.getString(R.string.msg_loading_file_error) } returns resultMsgErrorLoadingFake
        coEvery { loadCurrencyUseCase() } returns flowOf(UseCaseResult.Success(emptyList()))
        coEvery { loadFakeDataUseCase() } returns flowOf(UseCaseResult.Success(currencies))
        coEvery { fileUtils.readStringFromAssetFile("sample.json") } returns ""
        coEvery { insertCurrencyUseCase(currencies) } returns flowOf(
            UseCaseResult.Success(
                resultMsgErrorLoadingFake
            )
        )

        viewModel.check()

        assertEquals(resultMsgErrorLoadingFake, viewModel.message.value)
    }
}