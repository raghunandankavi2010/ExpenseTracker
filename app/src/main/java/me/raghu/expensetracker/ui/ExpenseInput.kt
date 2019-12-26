package me.raghu.expensetracker.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
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
import org.w3c.dom.Text
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
         binding =  DataBindingUtil.inflate(
            inflater, R.layout.expense_input_fragment, container, false)

        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.addExpenses.setOnClickListener{

        }


    }

}
