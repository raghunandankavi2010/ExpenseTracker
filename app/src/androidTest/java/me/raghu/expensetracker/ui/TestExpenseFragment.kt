package me.raghu.expensetracker.ui

import androidx.databinding.DataBindingComponent
import androidx.lifecycle.ViewModelProvider
import me.raghu.expensetracker.ui.expense.ExpenseFragment


class TestExpenseFragment: ExpenseFragment(){

        override fun injectMembers() {
            viewModelFactory = testViewModel
            dataBindingComponent = testDataBindingComponent
        }
        companion object {
            // static property can be set before launching the Fragment
            // to a mock instance
            lateinit var testViewModel: ViewModelProvider.Factory
            lateinit var testDataBindingComponent: DataBindingComponent
        }

}
