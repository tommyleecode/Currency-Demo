package rocks.tommylee.apps.currencydemo.usecase

import android.app.Application
import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import rocks.tommylee.apps.currencydemo.R
import rocks.tommylee.apps.currencydemo.database.data.Currency
import rocks.tommylee.apps.currencydemo.domain.usecase.LoadFakeDataUseCase
import rocks.tommylee.apps.currencydemo.domain.usecase.UseCaseResult
import rocks.tommylee.apps.currencydemo.utils.FileUtils
import rocks.tommylee.apps.currencydemo.utils.getListObject
import kotlin.test.assertEquals

class LoadFakeDataUseCaseTest {

    private val context: Application = mockk()
    private val fileUtils: FileUtils = mockk()
    private val loadFakeDataUseCase =
        LoadFakeDataUseCase(context = context, fileUtils = fileUtils)

    private var resultsData = ""

    @Before
    fun setup() {
        resultsData = getMockReadData()
    }

    @Test
    fun `load fake data successfully`() = runTest {
        coEvery { fileUtils.readStringFromAssetFile(any()) } returns resultsData

        loadFakeDataUseCase().test {
            assertEquals(UseCaseResult.Loading, awaitItem())
            assertEquals(UseCaseResult.Success(getStringToListObject()), awaitItem())
            awaitComplete()
        }

        coVerify(exactly = 1) { fileUtils.readStringFromAssetFile("sample.json") }
    }

    @Test
    fun `load fake data failure`() = runTest {
        val errorMsg = "Loading went wrong"

        coEvery { fileUtils.readStringFromAssetFile(any()) } returns ""
        coEvery { context.getString(R.string.msg_loading_file_error) } returns errorMsg

        loadFakeDataUseCase().test {
            assertEquals(UseCaseResult.Loading, awaitItem())
            assertEquals(UseCaseResult.Error(errorMsg), awaitItem())
            awaitComplete()
        }

        coVerify(exactly = 1) { fileUtils.readStringFromAssetFile("sample.json") }
    }

    private fun getStringToListObject(): List<Currency> {
        return getListObject(getMockReadData()) ?: emptyList()
    }

    private fun getMockReadData(): String {
        return "[\n" +
                "  {\n" +
                "    \"id\": \"BTC\",\n" +
                "    \"name\": \"Bitcoin\",\n" +
                "    \"symbol\": \"BTC\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"ETH\",\n" +
                "    \"name\": \"Ethereum\",\n" +
                "    \"symbol\": \"ETH\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"XRP\",\n" +
                "    \"name\": \"XRP\",\n" +
                "    \"symbol\": \"XRP\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"BCH\",\n" +
                "    \"name\": \"Bitcoin Cash\",\n" +
                "    \"symbol\": \"BCH\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"LTC\",\n" +
                "    \"name\": \"Litecoin\",\n" +
                "    \"symbol\": \"LTC\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"EOS\",\n" +
                "    \"name\": \"EOS\",\n" +
                "    \"symbol\": \"EOS\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"BNB\",\n" +
                "    \"name\": \"Binance Coin\",\n" +
                "    \"symbol\": \"BNB\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"LINK\",\n" +
                "    \"name\": \"Chainlink\",\n" +
                "    \"symbol\": \"LINK\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"NEO\",\n" +
                "    \"name\": \"NEO\",\n" +
                "    \"symbol\": \"NEO\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"ETC\",\n" +
                "    \"name\": \"Ethereum Classic\",\n" +
                "    \"symbol\": \"ETC\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"ONT\",\n" +
                "    \"name\": \"Ontology\",\n" +
                "    \"symbol\": \"ONT\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"CRO\",\n" +
                "    \"name\": \"Crypto.com Chain\",\n" +
                "    \"symbol\": \"CRO\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"CUC\",\n" +
                "    \"name\": \"Cucumber\",\n" +
                "    \"symbol\": \"CUC\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"USDC\",\n" +
                "    \"name\": \"USD Coin\",\n" +
                "    \"symbol\": \"USDC\"\n" +
                "  }\n" +
                "]"
    }
}