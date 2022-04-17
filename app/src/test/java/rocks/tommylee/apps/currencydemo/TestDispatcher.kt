package rocks.tommylee.apps.currencydemo

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import rocks.tommylee.apps.currencydemo.di.DispatcherProvider

class TestDispatcher : DispatcherProvider {
    private val testDispatcher = TestCoroutineDispatcher()
    override val main: CoroutineDispatcher
        get() = testDispatcher
    override val io: CoroutineDispatcher
        get() = testDispatcher
    override val default: CoroutineDispatcher
        get() = testDispatcher
}