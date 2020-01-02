package me.raghu.expensetracker.ui.editexpense

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.AndroidSupportInjection
import me.raghu.expensetracker.R
import me.raghu.expensetracker.databinding.ExpenseEditBinding

import me.raghu.expensetracker.db.Expense
import javax.inject.Inject


class EditExpenseFragment : Fragment() {

    private var expense: Expense? = null
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val editexpenseViewModel: ExpenseEditViewModel by viewModels {
        viewModelFactory
    }
    private lateinit var binding: ExpenseEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            expense = it.getParcelable("expense")
        }
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<ExpenseEditBinding>(
            inflater,
            R.layout.expense_edit,
            container,
            false
        )
        binding = dataBinding
        binding.lifecycleOwner = this
        binding.viewModel = editexpenseViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        expense?.let {
            editexpenseViewModel.id.value = it.id
            editexpenseViewModel.amount.value = it.expenseAmt
            editexpenseViewModel.expenseType.value = it.expenseType
            editexpenseViewModel.remarks.value = it.remarks
        }

        binding.editExpense?.setOnClickListener {
            editexpenseViewModel.updateExpenseToDb()
        }
    }

}