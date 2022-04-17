package rocks.tommylee.apps.currencydemo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule
import org.koin.test.KoinTest

open class BaseTest : KoinTest {
    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()
}