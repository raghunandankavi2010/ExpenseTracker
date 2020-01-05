package me.raghu.expensetracker.ui.expenseinput

import android.os.Bundle
import android.text.Editable
import android.view.*
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import me.raghu.expensetracker.R
import me.raghu.expensetracker.databinding.ExpenseInputFragmentBinding
import me.raghu.expensetracker.ui.databinding.FragmentDataBindingComponent
import me.raghu.expensetracker.ui.databinding.TextWatcherAdapter
import me.raghu.expensetracker.utils.autoCleared
import me.raghu.expensetracker.utils.toDateFormat
import javax.inject.Inject


class ExpenseInput : DaggerFragment() {

    companion object {
        fun newInstance() =
            ExpenseInput()
    }

    var binding by autoCleared<ExpenseInputFragmentBinding>()
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory


    private val expenseInputViewModel: ExpenseInputViewModel by viewModels {
        viewModelFactory
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.expense_input_fragment, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val dataBinding = DataBindingUtil.bind<ExpenseInputFragmentBinding>(view,dataBindingComponent)!!
        binding = dataBinding
        binding.lifecycleOwner = this
        binding.viewModel = expenseInputViewModel
        binding.addExpenses?.setOnClickListener {
            expenseInputViewModel.performValidation()
        }

        val model = activity?.let { ViewModelProviders.of(it).get(DateShareViewModel::class.java) }
        binding.dateSharedViewmodel = model

        model?.selectedDate?.observe(this, Observer {
            it?.let {
                expenseInputViewModel.selectedDate.value = it
            }
        })

        binding.showDatePicker?.setOnClickListener {
            val newFragment = DatePickerFragment()
            fragmentManager?.let { it1 -> newFragment.show(it1, "datePicker") }
        }

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

        expenseInputViewModel.insertedSuccessFully.observe(this, Observer {
            if(it){
                val snackbar = Snackbar
                        .make(binding.type, getString(R.string.expense_saved), Snackbar.LENGTH_LONG)

                snackbar.show()
                expenseInputViewModel.insertedSuccessFully.value = false
            }
        })
    }
}
