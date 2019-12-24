package me.raghu.expensetracker.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.expense_fragment.*
import me.raghu.expensetracker.R
import javax.inject.Inject


class ExpenseFragment : Fragment() {

    companion object {
        fun newInstance() = ExpenseFragment()
    }

    private lateinit var viewModel: ExpenseViewModel

    val searchViewModel: ExpenseViewModel by viewModels {
        viewModelFactory
    }


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

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
        viewModel = ViewModelProviders.of(this).get(ExpenseViewModel::class.java)
        add.setOnClickListener {
            it.findNavController().navigate(R.id.expenseInput)
        }
    }

}
