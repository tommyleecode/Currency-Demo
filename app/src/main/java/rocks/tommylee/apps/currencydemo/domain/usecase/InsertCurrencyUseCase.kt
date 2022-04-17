package rocks.tommylee.apps.currencydemo.domain.usecase

import android.app.Application
import kotlinx.coroutines.flow.flow
import rocks.tommylee.apps.currencydemo.R
import rocks.tommylee.apps.currencydemo.database.data.Currency
import rocks.tommylee.apps.currencydemo.domain.repository.CurrencyRepository

class InsertCurrencyUseCase(
    private val context: Application,
    private val currencyRepository: CurrencyRepository
) {
    suspend operator fun invoke(currencies: List<Currency>) = flow {
        emit(UseCaseResult.Loading)

        currencyRepository.insert(currencies)

        emit(UseCaseResult.Success(context.getString(R.string.msg_data_inserted_successfully)))
    }
}