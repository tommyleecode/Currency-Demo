package rocks.tommylee.apps.currencydemo.domain.repository

import rocks.tommylee.apps.currencydemo.database.dao.CurrencyDao
import rocks.tommylee.apps.currencydemo.database.data.Currency

class CurrencyRepository(private val currencyDao: CurrencyDao) {
    suspend fun all(toSort: Boolean): List<Currency> {
        return if (toSort) {
            currencyDao.all().sortedByDescending { it.name }
        } else {
            currencyDao.all()
        }
    }

    suspend fun insert(currencies: List<Currency>) {
        currencies.forEach {
            currencyDao.insert(it)
        }
    }
}