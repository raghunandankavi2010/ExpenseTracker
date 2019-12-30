package me.raghu.expensetracker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerFragment
import me.raghu.expensetracker.R
import me.raghu.expensetracker.databinding.ExpenseInputFragmentBinding
import me.raghu.expensetracker.ui.databinding.FragmentDataBindingComponent
import me.raghu.expensetracker.utils.autoCleared
import javax.inject.Inject


class ExpenseInput : DaggerFragment() {

    companion object {
        fun newInstance() = ExpenseInput()
    }

    var binding by autoCleared<ExpenseInputFragmentBinding>()
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory


    private val expenseInputViewModel: ExpenseInputViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<ExpenseInputFragmentBinding>(
            inflater,
            R.layout.expense_input_fragment,
            container,
            false,
            dataBindingComponent
        )
        binding = dataBinding
        binding.lifecycleOwner = this
        binding.viewModel = expenseInputViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.addExpenses.setOnClickListener {
            expenseInputViewModel.performValidation()
        }

        expenseInputViewModel.expenseType.observe(this, Observer {
            if (it.isNotEmpty()) {
                expenseInputViewModel.expenseTypeError.value = ""
            }
        })

        expenseInputViewModel.amount.observe(this, Observer {
            if (it.isNotEmpty()) {
                expenseInputViewModel.amountError.value = ""
            }
        })

        expenseInputViewModel.remarks.observe(this, Observer {
            if (it.isNotEmpty()) {
                expenseInputViewModel.remarksError.value = ""
            }
        })
    }

}
