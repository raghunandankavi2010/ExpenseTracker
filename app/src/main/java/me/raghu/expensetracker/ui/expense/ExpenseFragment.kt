package me.raghu.expensetracker.ui.expense

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import dagger.android.support.AndroidSupportInjection
import me.raghu.expensetracker.R
import me.raghu.expensetracker.databinding.ExpenseFragmentBinding
import me.raghu.expensetracker.ui.MainActivity
import me.raghu.expensetracker.ui.databinding.FragmentDataBindingComponent
import me.raghu.expensetracker.utils.*
import java.util.*
import java.util.concurrent.Executors
import javax.inject.Inject


class ExpenseFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    companion object {
        fun newInstance() = ExpenseFragment()
    }

    private val expenseViewModel: ExpenseViewModel by viewModels {
        viewModelFactory
    }
    private lateinit var binding: ExpenseFragmentBinding

    private var adapter by autoCleared<ExpenseAdapter>()
    private var dividerItemDecoration: DividerItemDecoration? = null
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
        val ctx = activity as MainActivity
        ctx.supportActionBar?.apply {
            setHomeAsUpIndicator(R.drawable.ic_home)
            setDisplayHomeAsUpEnabled(true)
        }
        this.adapter = expenseAdapter
        binding.main.expenseList.adapter = adapter
        if (binding.main.expenseList.itemDecorationCount == 0) {
            dividerItemDecoration = context?.let { DividerItemDecoration(it) }
            dividerItemDecoration?.let { binding.main.expenseList.addItemDecoration(it) }
        }
        expenseViewModel.expense.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            adapter.submitList(it)
        })
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        ViewCompat.requestApplyInsets(view)
        binding.main.nestedScroll.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        binding.root.let {
            ViewCompat.setOnApplyWindowInsetsListener(it) { v, insets ->
                ViewCompat.onApplyWindowInsets(
                    v, insets.replaceSystemWindowInsets(
                        insets.systemWindowInsetLeft, 0,
                        insets.systemWindowInsetRight, insets.systemWindowInsetBottom
                    )
                )
                insets // return original insets to pass them down in view hierarchy or remove this line if you want to pass modified insets down the stream.

            }
        }

        binding.main.expenseList.addSystemWindowInsetToMargin(bottom = true)
        binding.add.addSystemWindowInsetToMargin(bottom = true)

        PreferenceManager.setDefaultValues(activity, R.xml.preferences, false)
        val sharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(activity /* Activity context */)
        val sharedPreferenceStringLiveData =
            SharedPreferenceStringLiveData(sharedPreferences, "income_monthly", "")
        sharedPreferenceStringLiveData.getStringLiveData("income_monthly", "").observe(this,
            androidx.lifecycle.Observer { incomeValue: String ->
                binding.main.layoutAccountExpenditureDetails.monthlyIncome.text =
                    activity?.resources?.getString(
                        R.string.m_income, incomeValue,
                        Currency.getInstance(Locale.getDefault()).getSymbol(Locale.getDefault())
                    )
                expenseViewModel.expenseExceeded.value = incomeValue.toFloat()
            }
        )

        binding.add.setOnClickListener {
            it.findNavController().navigate(R.id.expenseInput)
        }

        expenseViewModel.setDateRange(getFirstDateOfMonth(), getLastDateOfMonth())

    }

    private fun pxFromDp(dp: Float): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }

}
