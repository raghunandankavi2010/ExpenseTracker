package me.raghu.expensetracker.ui.editexpense

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import me.raghu.expensetracker.R
import me.raghu.expensetracker.databinding.ExpenseEditBinding
import me.raghu.expensetracker.db.Expense
import me.raghu.expensetracker.ui.expenseinput.DatePickerFragment
import me.raghu.expensetracker.ui.expenseinput.DateShareViewModel
import java.util.*
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
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
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

        val model = activity?.let { ViewModelProviders.of(it).get(ShareDateModel::class.java) }
        binding.dateSharedViewModel = model

        model?.selectedDate?.observe(this, Observer {
            it?.let {
                editexpenseViewModel.selectedDate.value = it
            }
        })

        binding.showDatePicker.setOnClickListener {
            val newFragment = DatePicker()
            fragmentManager?.let { it1 -> newFragment.show(it1, "datePicker") }
        }

        expense?.let {
            editexpenseViewModel.selectedDate.value = it.date
            editexpenseViewModel.id.value = it.id
            editexpenseViewModel.amount.value = it.expenseAmt
            editexpenseViewModel.expenseType.value = it.expenseType
            editexpenseViewModel.remarks.value = it.remarks
        }

        binding.editExpense.setOnClickListener {
            editexpenseViewModel.updateExpenseToDb()
        }

        editexpenseViewModel.editedSuccessFully.observe(this, Observer {
            if(it){
                val snackbar = Snackbar
                    .make(binding.type, getString(R.string.expense_saved), Snackbar.LENGTH_LONG)

                snackbar.show()
                editexpenseViewModel.editedSuccessFully.value = false
            }
        })
    }

}
