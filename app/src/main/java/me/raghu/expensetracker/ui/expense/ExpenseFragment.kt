package me.raghu.expensetracker.ui.expense

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.expense_fragment.*
import me.raghu.expensetracker.R
import me.raghu.expensetracker.databinding.ExpenseFragmentBinding
import me.raghu.expensetracker.ui.databinding.FragmentDataBindingComponent
import me.raghu.expensetracker.utils.SharedPreferenceStringLiveData
import me.raghu.expensetracker.utils.autoCleared
import me.raghu.expensetracker.utils.getFirstDateOfMonth
import me.raghu.expensetracker.utils.getLastDateOfMonth
import java.util.*
import java.util.concurrent.Executors
import javax.inject.Inject


class ExpenseFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    companion object {
        fun newInstance() = ExpenseFragment()
    }

    val expenseViewModel: ExpenseViewModel by viewModels {
        viewModelFactory
    }
    private lateinit var binding: ExpenseFragmentBinding

    private var adapter by autoCleared<ExpenseAdapter>()
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<ExpenseFragmentBinding>(
            inflater,
            R.layout.expense_fragment,
            container,
            false
        )
        binding = dataBinding
        binding.lifecycleOwner = this
        binding.viewModel = expenseViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity /* Activity context */)
        val sharedPreferenceStringLiveData = SharedPreferenceStringLiveData(sharedPreferences, "income_monthly", "")
        sharedPreferenceStringLiveData.getStringLiveData("income_monthly", "").observe(this,
            androidx.lifecycle.Observer { incomeValue: String ->
                binding.main.monthlyIncome.text = incomeValue
                expenseViewModel.expenseExceeded.value = incomeValue.toFloat()
            }
        )

        add.setOnClickListener {
            it.findNavController().navigate(R.id.expenseInput)
        }
        val date = Date()
        val newSingleThreadExecutor = Executors.newSingleThreadExecutor()
        expenseViewModel.setDateRange(date.getFirstDateOfMonth(), getLastDateOfMonth())
        val expenseAdapter = ExpenseAdapter(
            dataBindingComponent = dataBindingComponent, appExecutors = newSingleThreadExecutor
        ) { expenseEvent ->
            expenseEvent.let {
                if (expenseEvent.type == "delete") {
                    expenseViewModel.deleteExpense(expenseEvent.expense.id)
                } else if (expenseEvent.type == "edit") {
                    val bundle = bundleOf("expense" to expenseEvent.expense)
                    findNavController().navigate(
                        R.id.action_expenseFragment_to_editExpenseFragment,
                        bundle
                    )
                }
            }
        }

        this.adapter = expenseAdapter
        binding.main.expenseList.adapter = adapter
        val mDividerItemDecoration = context?.let {
            DividerItemDecoration(it)
        }
        mDividerItemDecoration?.let { binding.main.expenseList.addItemDecoration(it) }
        initExpenseList()
    }

    private fun initExpenseList() {
        expenseViewModel.expense.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            adapter.submitList(it)
        })
    }

}
