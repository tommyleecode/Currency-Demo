package rocks.tommylee.apps.currencydemo.ui.currency

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import rocks.tommylee.apps.currencydemo.database.data.Currency
import rocks.tommylee.apps.currencydemo.databinding.ItemCurrencyBinding

class CurrencyAdapter(
    private val currencies: List<Currency>,
    private val onCurrencyClick: (Currency) -> Unit,
) : RecyclerView.Adapter<CurrencyAdapter.CurrencyHolder>() {

    class CurrencyHolder(private val itemBinding: ItemCurrencyBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(currency: Currency, onCurrencyClick: (Currency) -> Unit) {
            itemBinding.currencyInitialText.text = currency.name[0].toString()
            itemBinding.currencyText.text = currency.name
            itemBinding.currencyText.text = currency.symbol
            itemBinding.currencyItem.setOnClickListener { onCurrencyClick(currency) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyHolder {
        val itemBinding =
            ItemCurrencyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CurrencyHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CurrencyHolder, position: Int) {
        val item = currencies[position]
        holder.bind(item, onCurrencyClick)
    }

    override fun getItemCount() = currencies.size
}