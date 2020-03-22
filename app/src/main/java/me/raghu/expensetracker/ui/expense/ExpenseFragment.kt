package me.raghu.expensetracker.ui.expense

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.updatePaddingRelative
import androidx.databinding.DataBindingComponent
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import com.google.android.material.transition.Hold
import dagger.android.support.AndroidSupportInjection
import me.raghu.expensetracker.R
import me.raghu.expensetracker.databinding.ExpenseFragmentBinding
import me.raghu.expensetracker.ui.MainNavigationFragment
import me.raghu.expensetracker.ui.databinding.FragmentDataBindingComponent
import me.raghu.expensetracker.utils.*
import java.util.*
import java.util.concurrent.Executors
import javax.inject.Inject


class ExpenseFragment : MainNavigationFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    companion object {
        fun newInstance() = ExpenseFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        exitTransition =  Hold()

    }

    private val expenseViewModel: ExpenseViewModel by viewModels {
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

        binding = ExpenseFragmentBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@ExpenseFragment.expenseViewModel
        }

        binding.lifecycleOwner = this
        binding.viewModel = expenseViewModel

        val newSingleThreadExecutor = Executors.newSingleThreadExecutor()
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
        val dividerItemDecoration =  DividerItemDecoration(activity as Context)
        binding.main.expenseList.addItemDecoration(dividerItemDecoration)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.main.expenseList.doOnApplyWindowInsets { v, insets, padding ->
            v.updatePaddingRelative(bottom = padding.bottom + insets.systemWindowInsetBottom)
        }

        expenseViewModel.expense.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            adapter.submitList(it)
        })

        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            setHomeAsUpIndicator(R.drawable.ic_home)
            setDisplayHomeAsUpEnabled(true)
        }


        if (savedInstanceState == null) {
            binding.coordinatorLayout.postDelayed({
                binding.coordinatorLayout.requestApplyInsetsWhenAttached()
            }, 200)
        }

        PreferenceManager.setDefaultValues(activity, R.xml.preferences, false)
        val sharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(activity /* Activity context */)
        val sharedPreferenceStringLiveData =
            SharedPreferenceStringLiveData(sharedPreferences, "income_monthly", "")
        sharedPreferenceStringLiveData.getStringLiveData("income_monthly", "").observe(viewLifecycleOwner,
            androidx.lifecycle.Observer { incomeValue: String ->
                binding.main.layoutAccountExpenditureDetails.monthlyIncome.text =
                    activity?.resources?.getString(
                        R.string.m_income, incomeValue,
                        Currency.getInstance(Locale.getDefault()).getSymbol(Locale.getDefault())
                    )
                expenseViewModel.expenseExceeded.value = incomeValue.toFloat()
            }
        )
        val extras =  FragmentNavigatorExtras(binding.add to "shared_element_end_root")
        binding.add.setOnClickListener {
            findNavController().navigate(R.id.expenseInput, null, null, extras)
        }

        expenseViewModel.setDateRange(getFirstDateOfMonth(), getLastDateOfMonth())

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean { // Handle item selection
        return when (item.itemId) {
            R.id.secondActivity -> {
                findNavController().navigate(R.id.secondActivity)
                true
            }
            R.id.lineChartFragment -> {
                findNavController().navigate(R.id.lineChartFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
