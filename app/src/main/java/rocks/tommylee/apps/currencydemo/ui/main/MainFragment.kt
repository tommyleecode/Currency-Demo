package rocks.tommylee.apps.currencydemo.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import rocks.tommylee.apps.currencydemo.R
import rocks.tommylee.apps.currencydemo.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeBindings()

        binding.loadBtn.setOnClickListener {
            viewModel.check()
        }

        binding.openBtn.setOnClickListener {
            it.findNavController().navigate(R.id.currencyListFragment)
        }
    }

    private fun observeBindings() {
        viewModel.message.observe(viewLifecycleOwner) {
            it?.let {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}