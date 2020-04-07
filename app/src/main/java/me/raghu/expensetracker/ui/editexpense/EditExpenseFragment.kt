package me.raghu.expensetracker.ui.editexpense

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginLeft
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import me.raghu.expensetracker.R
import me.raghu.expensetracker.databinding.ExpenseEditBinding
import me.raghu.expensetracker.db.Expense
import me.raghu.expensetracker.ui.MainNavigationFragment
import me.raghu.expensetracker.utils.addSystemWindowInsetToMargin
import javax.inject.Inject


class EditExpenseFragment : MainNavigationFragment() {

    private var expense: Expense? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val editexpenseViewModel: ExpenseEditViewModel by viewModels {
        viewModelFactory
    }

    private val dateShareViewModel by activityViewModels<ShareDateModel>()

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
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp)
            setDisplayHomeAsUpEnabled(true)
        }

        val orientation = resources.configuration.orientation
        val margin =  binding.coordinator?.marginLeft
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) { // In landscape
            margin?.let { binding.coordinator?.addSystemWindowInsetToMargin(left = true) }
        }

        binding.dateSharedViewModel = dateShareViewModel

        dateShareViewModel.selectedDate.observe(viewLifecycleOwner, Observer {
            it?.let {
                editexpenseViewModel.selectedDate.value = it
            }
        })

        binding.showDatePicker.setOnClickListener {
            val newFragment = DatePicker()
            parentFragmentManager.let { it1 -> newFragment.show(it1, "datePicker") }
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

        editexpenseViewModel.editedSuccessFully.observe(viewLifecycleOwner, Observer {
            if(it){
                val snackbar = Snackbar
                    .make(binding.type, getString(R.string.expense_saved), Snackbar.LENGTH_LONG)

                snackbar.show()
                editexpenseViewModel.editedSuccessFully.value = false
            }
        })
    }

}
