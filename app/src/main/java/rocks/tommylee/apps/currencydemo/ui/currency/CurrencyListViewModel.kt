package rocks.tommylee.apps.currencydemo.ui.currency

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import rocks.tommylee.apps.currencydemo.database.data.Currency
import rocks.tommylee.apps.currencydemo.di.DefaultCoroutineDispatchers
import rocks.tommylee.apps.currencydemo.di.DispatcherProvider
import rocks.tommylee.apps.currencydemo.domain.usecase.LoadCurrencyUseCase
import rocks.tommylee.apps.currencydemo.domain.usecase.UseCaseResult
import timber.log.Timber

class CurrencyListViewModel(
    private val dispatcher: DispatcherProvider,
    private val loadCurrencyUseCase: LoadCurrencyUseCase
) : ViewModel() {
    private val _data: MutableLiveData<List<Currency>> = MutableLiveData()
    val data: LiveData<List<Currency>>
        get() = _data

    private var toggleSort: Boolean = false

    init {
        load(toggleSort)
    }

    @ExperimentalCoroutinesApi
    fun load(toSort: Boolean) {
        viewModelScope.launch(dispatcher.io) {
            loadCurrencyUseCase(toSort).mapLatest { it }.collect {
                when (it) {
                    is UseCaseResult.Error -> {}
                    UseCaseResult.Loading -> {}
                    is UseCaseResult.Success -> {
                        _data.postValue(it.result)
                    }
                }
            }
        }
    }

    fun sort() {
        viewModelScope.launch(dispatcher.io) {
            toggleSort = !toggleSort
            load(toggleSort)
        }
    }
}