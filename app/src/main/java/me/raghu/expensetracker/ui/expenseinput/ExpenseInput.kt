package me.raghu.expensetracker.ui.expenseinput

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import me.raghu.expensetracker.R
import me.raghu.expensetracker.databinding.ExpenseInputFragmentBinding
import me.raghu.expensetracker.ui.MainNavigationFragment
import me.raghu.expensetracker.ui.databinding.FragmentDataBindingComponent
import me.raghu.expensetracker.utils.autoCleared
import me.raghu.expensetracker.utils.requestApplyInsetsWhenAttached
import javax.inject.Inject

/**
 *  Displays views to add Expense's
 */
class ExpenseInput : MainNavigationFragment() {

    companion object {
        fun newInstance() =
            ExpenseInput()
    }

    var binding by autoCleared<ExpenseInputFragmentBinding>()
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    private val dateShareViewModel by activityViewModels<DateShareViewModel>()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val expenseInputViewModel: ExpenseInputViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.expense_input_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dataBinding =
            DataBindingUtil.bind<ExpenseInputFragmentBinding>(view, dataBindingComponent)!!
        binding = dataBinding

        binding.lifecycleOwner = this
        binding.viewModel = expenseInputViewModel
        binding.addExpenses.setOnClickListener {
            expenseInputViewModel.performValidation()
        }

        if (savedInstanceState == null) {

            binding.coordinator?.let {
                binding.coordinator?.requestApplyInsetsWhenAttached()
            }
        }

        binding.dateSharedViewmodel = dateShareViewModel

        dateShareViewModel.selectedDate.observe(viewLifecycleOwner, Observer {
            it?.let {
                expenseInputViewModel.selectedDate.value = it
            }
        })

        binding.showDatePicker.setOnClickListener {
            val newFragment = DatePickerFragment()
            parentFragmentManager.let { it1 -> newFragment.show(it1, "datePicker") }
        }

        expenseInputViewModel.amount.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                expenseInputViewModel.amountError.value = ""
            }
        })

        expenseInputViewModel.remarks.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                expenseInputViewModel.remarksError.value = ""
            }
        })

        expenseInputViewModel.insertedSuccessFully.observe(viewLifecycleOwner, Observer {
            if (it) {
                val snackbar = Snackbar
                    .make(binding.type, getString(R.string.expense_saved), Snackbar.LENGTH_LONG)

                snackbar.show()
                expenseInputViewModel.insertedSuccessFully.value = false
            }
        })
    }
}
