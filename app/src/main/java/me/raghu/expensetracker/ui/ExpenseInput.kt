package me.raghu.expensetracker.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import me.raghu.expensetracker.R

class ExpenseInput : Fragment() {

    companion object {
        fun newInstance() = ExpenseInput()
    }

    private lateinit var viewModel: ExpenseInputViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.expense_input_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ExpenseInputViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
