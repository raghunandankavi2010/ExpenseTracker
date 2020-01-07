package me.raghu.expensetracker.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import me.raghu.expensetracker.ui.chart.LineChartViewModel
import me.raghu.expensetracker.ui.editexpense.ExpenseEditViewModel
import me.raghu.expensetracker.ui.expense.ExpenseViewModel
import me.raghu.expensetracker.ui.expenseinput.ExpenseInputViewModel
import me.raghu.expensetracker.viewmodel.ExpenseTrackerViewModelFactory

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ExpenseViewModel::class)
    abstract fun bindExpenseViewModel(expenseViewModel: ExpenseViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ExpenseInputViewModel::class)
    abstract fun bindExpenseInputViewModel(expenseInputViewModel: ExpenseInputViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ExpenseEditViewModel::class)
    abstract fun bindExpenseEditViewModel(expenseEditViewModel: ExpenseEditViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LineChartViewModel::class)
    abstract fun bindLineChartViewModel(lineChartViewModel: LineChartViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ExpenseTrackerViewModelFactory): ViewModelProvider.Factory


}
