package me.raghu.expensetracker.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.expense_fragment.*
import me.raghu.expensetracker.R
import java.util.*
import javax.inject.Inject


class ExpenseFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    companion object {
        fun newInstance() = ExpenseFragment()
    }

    private lateinit var viewModel: ExpenseViewModel

    val expenseViewModel: ExpenseViewModel by viewModels {
        viewModelFactory
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.expense_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        add.setOnClickListener {
            it.findNavController().navigate(R.id.expenseInput)
        }
        expenseViewModel.expenses.observe(this, Observer { expenses ->
            expenses?.let {
                if (it.isNotEmpty())
                    Log.i("Expenses", "" + it[0].expenseAmt)
            }
        })

        expenseViewModel.setDateRange(getFirstDateOfMonth(Date()), getlastDateOfMonth())
        expenseViewModel.toatalExpenseCurrentMonth.observe(this, Observer { total ->
            total?.let {
                total_amt_spent_value.text = getString(R.string.amount, "Spent", it)
            }
        })
    }

    fun getlastDateOfMonth(): Date {
        val cal: Calendar = Calendar.getInstance()
        val lastDate: Int = cal.getActualMaximum(Calendar.DATE)
        cal.set(Calendar.DAY_OF_MONTH, lastDate)
        return cal.time
    }

    fun getFirstDateOfMonth(date: Date): Date {
        val cal = Calendar.getInstance()
        cal.time = date
        cal[Calendar.DAY_OF_MONTH] = cal.getActualMinimum(Calendar.DAY_OF_MONTH)
        return cal.time
    }
}
