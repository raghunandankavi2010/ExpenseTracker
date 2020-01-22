package me.raghu.expensetracker.ui.expenseinput

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.marginLeft
import androidx.core.view.updatePadding
import androidx.core.view.updatePaddingRelative
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.activity_main.*
import me.raghu.expensetracker.R
import me.raghu.expensetracker.databinding.ExpenseInputFragmentBinding
import me.raghu.expensetracker.ui.databinding.FragmentDataBindingComponent
import me.raghu.expensetracker.utils.addSystemWindowInsetToMargin
import me.raghu.expensetracker.utils.autoCleared
import me.raghu.expensetracker.utils.doOnApplyWindowInsets
import me.raghu.expensetracker.utils.requestApplyInsetsWhenAttached
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
        binding.addExpenses.setOnClickListener {
            expenseInputViewModel.performValidation()
        }
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp)
            setDisplayHomeAsUpEnabled(true)
        }
        binding.coordinator?.doOnApplyWindowInsets { v, insets, padding ->
            v.updatePaddingRelative(top = padding.top + insets.systemWindowInsetTop)
        }

        if (savedInstanceState == null) {

            binding.coordinator?.postDelayed({
                binding.coordinator?.requestApplyInsetsWhenAttached()
            }, 500)
        }
        val orientation = resources.configuration.orientation
        val margin =  binding.coordinator?.marginLeft
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) { // In landscape
            if (margin != null) {
                binding.coordinator?.addSystemWindowInsetToMargin(top = true)
            }
        }
        val model = activity?.let { ViewModelProviders.of(it).get(DateShareViewModel::class.java) }
        binding.dateSharedViewmodel = model

        model?.selectedDate?.observe(this, Observer {
            it?.let {
                expenseInputViewModel.selectedDate.value = it
            }
        })

        binding.showDatePicker.setOnClickListener {
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
