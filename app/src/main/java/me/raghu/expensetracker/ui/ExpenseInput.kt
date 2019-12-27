package me.raghu.expensetracker.ui

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.expense_input_fragment.*
import me.raghu.expensetracker.R
import me.raghu.expensetracker.databinding.ExpenseInputFragmentBinding
import me.raghu.expensetracker.db.Expense
import javax.inject.Inject


class ExpenseInput : DaggerFragment() {

    companion object {
        fun newInstance() = ExpenseInput()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory


    val expenseInputViewModel: ExpenseInputViewModel by viewModels {
        viewModelFactory
    }
    private lateinit var binding: ExpenseInputFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.expense_input_fragment, container, false
        )
        binding.lifecycleOwner = this
        binding.viewModel = expenseInputViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.addExpenses.setOnClickListener {
            expenseInputViewModel.performValidation()
        }
    }

}
