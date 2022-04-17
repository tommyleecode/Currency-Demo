package rocks.tommylee.apps.currencydemo.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rocks.tommylee.apps.currencydemo.domain.repository.CurrencyRepository
import rocks.tommylee.apps.currencydemo.domain.usecase.InsertCurrencyUseCase
import rocks.tommylee.apps.currencydemo.domain.usecase.LoadCurrencyUseCase
import rocks.tommylee.apps.currencydemo.domain.usecase.LoadFakeDataUseCase
import rocks.tommylee.apps.currencydemo.ui.currency.CurrencyListViewModel
import rocks.tommylee.apps.currencydemo.ui.main.MainViewModel

private val viewModels = module {
    viewModel { CurrencyListViewModel(get(), get()) }
    viewModel { MainViewModel(get(), get(), get(), get(), get()) }
}

private val repository = module {
    single { CurrencyRepository(get()) }
}

private val useCases = module {
    single { LoadFakeDataUseCase(get(), get()) }
    single { LoadCurrencyUseCase(get()) }
    single { InsertCurrencyUseCase(get(), get()) }
}

private val components = module {
    single { provideDatabase(get()) }
    single { provideCurrencyDao(get()) }
    single<DispatcherProvider> { DefaultCoroutineDispatchers() }
}

private val utils = module {
    single { provideFileUtils(get()) }
}

val allModules = components + utils + viewModels + repository + useCases