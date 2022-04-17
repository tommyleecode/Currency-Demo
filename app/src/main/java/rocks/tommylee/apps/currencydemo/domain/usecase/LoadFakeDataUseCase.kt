package rocks.tommylee.apps.currencydemo.domain.usecase

import android.app.Application
import kotlinx.coroutines.flow.flow
import rocks.tommylee.apps.currencydemo.R
import rocks.tommylee.apps.currencydemo.database.data.Currency
import rocks.tommylee.apps.currencydemo.utils.FileUtils
import rocks.tommylee.apps.currencydemo.utils.getListObject

class LoadFakeDataUseCase(
    private val context: Application,
    private val fileUtils: FileUtils
) {
    suspend operator fun invoke() = flow {
        emit(UseCaseResult.Loading)

        kotlin.runCatching {
            val result = fileUtils.readStringFromAssetFile("sample.json")

            if (result.isNotEmpty()) {
                getListObject<Currency>(result) ?: emptyList()
            } else {
                throw Exception("Read out from json file cannot be empty")
            }
        }.onSuccess {
            emit(UseCaseResult.Success(it))
        }.onFailure {
            emit(UseCaseResult.Error(msg = context.getString(R.string.msg_loading_file_error)))
        }
    }
}