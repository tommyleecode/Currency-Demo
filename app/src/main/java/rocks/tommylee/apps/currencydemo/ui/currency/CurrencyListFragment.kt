package rocks.tommylee.apps.currencydemo.ui.currency

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import rocks.tommylee.apps.currencydemo.database.data.Currency
import rocks.tommylee.apps.currencydemo.databinding.CurrencyListFragmentBinding

class CurrencyListFragment : Fragment() {

    private val viewModel: CurrencyListViewModel by viewModel()

    private var _binding: CurrencyListFragmentBinding? = null
    private val binding get() = _binding!!

    private var adapter: CurrencyAdapter =
        CurrencyAdapter(emptyList()) { currency: Currency -> itemClick(currency) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CurrencyListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.currencyList.layoutManager = LinearLayoutManager(context)

        viewModel.data.observe(viewLifecycleOwner) {
            it?.let {
                adapter = CurrencyAdapter(it) { currency: Currency -> itemClick(currency) }
                binding.currencyList.adapter = adapter
            }
        }

        binding.sortBtn.setOnClickListener {
            viewModel.sort()
        }
    }

    private fun itemClick(item: Currency) {
        Toast.makeText(context, "${item.name} is clicked", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}