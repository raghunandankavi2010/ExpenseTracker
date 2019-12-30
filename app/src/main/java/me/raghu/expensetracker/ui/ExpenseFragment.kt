package me.raghu.expensetracker.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.expense_fragment.*
import me.raghu.expensetracker.R
import me.raghu.expensetracker.databinding.ExpenseFragmentBinding
import me.raghu.expensetracker.ui.databinding.FragmentDataBindingComponent
import me.raghu.expensetracker.utils.autoCleared
import me.raghu.expensetracker.utils.getFirstDateOfMonth
import me.raghu.expensetracker.utils.getLastDateOfMonth
import java.util.*
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

        add.setOnClickListener {
            it.findNavController().navigate(R.id.expenseInput)
        }
        val date = Date()
        expenseViewModel.setDateRange(date.getFirstDateOfMonth(), getLastDateOfMonth())
        val expenseAdapter = ExpenseAdapter(dataBindingComponent = dataBindingComponent) { expense ->
            expense.let {

            }
        }
        this.adapter = expenseAdapter
        binding.main.expenseList.adapter = adapter
        val  mDividerItemDecoration = DividerItemDecoration(
           binding.main.expenseList.context,DividerItemDecoration.HORIZONTAL)
        binding.main.expenseList.addItemDecoration(mDividerItemDecoration)
        initExpenseList()
    }

    private fun initExpenseList() {
        expenseViewModel.expenses.observe(viewLifecycleOwner, androidx.lifecycle.Observer  {
            list -> list?.let{ adapter.submitList(it)}

        })
    }


}
