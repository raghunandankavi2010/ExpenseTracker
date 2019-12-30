package me.raghu.expensetracker.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.expense_fragment.*
import me.raghu.expensetracker.R
import me.raghu.expensetracker.databinding.ExpenseFragmentBinding
import me.raghu.expensetracker.utils.getFirstDateOfMonth
import me.raghu.expensetracker.utils.getLastDateOfMonth
import java.util.*
import javax.inject.Inject


class ExpenseFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    companion object {
        fun newInstance() = ExpenseFragment()
    }

    val expenseViewModel: ExpenseViewModel by viewModels {
        viewModelFactory
    }
    private lateinit var binding: ExpenseFragmentBinding

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = DataBindingUtil.inflate(
            inflater, R.layout.expense_fragment, container, false
        )

        binding.lifecycleOwner = this
        binding.viewModel = expenseViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        add.setOnClickListener {
            it.findNavController().navigate(R.id.expenseInput)
        }
        val date = Date()
        expenseViewModel.setDateRange(date.getFirstDateOfMonth(), getLastDateOfMonth())
    }
}
