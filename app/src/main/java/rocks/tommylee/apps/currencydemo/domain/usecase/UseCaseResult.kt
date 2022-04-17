package rocks.tommylee.apps.currencydemo.domain.usecase

sealed class UseCaseResult<out T : Any> {
    object Loading : UseCaseResult<Nothing>()
    data class Success<T : Any>(val result: T) : UseCaseResult<T>()
    data class Error(val msg: String, val e: Throwable? = null) : UseCaseResult<Nothing>()
}
