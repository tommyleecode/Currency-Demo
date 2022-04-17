package rocks.tommylee.apps.currencydemo.domain.usecase

import kotlinx.coroutines.flow.flow
import rocks.tommylee.apps.currencydemo.domain.repository.CurrencyRepository

class LoadCurrencyUseCase(private val currencyRepository: CurrencyRepository) {
    suspend operator fun invoke(toSort: Boolean = false) = flow {
        emit(UseCaseResult.Loading)

        val result = currencyRepository.all(toSort)

        emit(UseCaseResult.Success(result))
    }
}