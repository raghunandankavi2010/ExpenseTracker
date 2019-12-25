package me.raghu.expensetracker.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.expense_input_fragment.*

import me.raghu.expensetracker.R
import javax.inject.Inject

class ExpenseInput : Fragment() {

    companion object {
        fun newInstance() = ExpenseInput()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: ExpenseInputViewModel

    val expenseInputViewModel: ExpenseInputViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.expense_input_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ExpenseInputViewModel::class.java)

        var expenseType: String? = null
        addExpenses.setOnClickListener{
            if(TextUtils.isEmpty(expense_type.text.toString())){
                expenseType = expense_type.text.toString()
            }else {
                type.error = "Please Enter Expense Type"
            }
        }
    }

}
