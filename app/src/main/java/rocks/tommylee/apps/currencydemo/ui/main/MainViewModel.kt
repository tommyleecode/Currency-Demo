package rocks.tommylee.apps.currencydemo.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import rocks.tommylee.apps.currencydemo.R
import rocks.tommylee.apps.currencydemo.database.data.Currency
import rocks.tommylee.apps.currencydemo.di.DispatcherProvider
import rocks.tommylee.apps.currencydemo.domain.usecase.InsertCurrencyUseCase
import rocks.tommylee.apps.currencydemo.domain.usecase.LoadCurrencyUseCase
import rocks.tommylee.apps.currencydemo.domain.usecase.LoadFakeDataUseCase
import rocks.tommylee.apps.currencydemo.domain.usecase.UseCaseResult

class MainViewModel(
    private val dispatcher: DispatcherProvider,
    private val context: Application,
    private val loadFakeDataUseCase: LoadFakeDataUseCase,
    private val loadCurrencyUseCase: LoadCurrencyUseCase,
    private val insertCurrencyUseCase: InsertCurrencyUseCase
) : ViewModel() {

    private val _message: MutableLiveData<String> = MutableLiveData()
    val message: LiveData<String>
        get() = _message

    fun check() {
        viewModelScope.launch(dispatcher.io) {
            loadCurrencyUseCase().collect {
                when (it) {
                    is UseCaseResult.Error -> {}
                    UseCaseResult.Loading -> {}
                    is UseCaseResult.Success -> {
                        if (it.result.isEmpty()) {
                            loadFromFile()
                        } else {
                            toast(context.getString(R.string.toast_data_already_inserted))
                        }
                    }
                }
            }
        }
    }

    private fun loadFromFile() {
        viewModelScope.launch(dispatcher.io) {
            loadFakeDataUseCase().collect {
                when (it) {
                    is UseCaseResult.Error -> {
                        toast(it.msg)
                    }
                    UseCaseResult.Loading -> {}
                    is UseCaseResult.Success -> {
                        insertData(it.result)
                    }
                }
            }
        }
    }

    private fun insertData(currencies: List<Currency>) {
        viewModelScope.launch(dispatcher.io) {
            insertCurrencyUseCase(currencies).collect {
                when (it) {
                    is UseCaseResult.Error -> {
                        toast(it.msg)
                    }
                    UseCaseResult.Loading -> {}
                    is UseCaseResult.Success -> {
                        toast(it.result)
                    }
                }
            }
        }
    }

    private fun toast(message: String) {
        _message.postValue(message)
    }
}