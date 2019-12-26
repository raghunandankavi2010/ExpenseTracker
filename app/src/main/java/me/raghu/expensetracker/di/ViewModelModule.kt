package me.raghu.expensetracker.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import me.raghu.expensetracker.ui.ExpenseInputViewModel
import me.raghu.expensetracker.ui.ExpenseViewModel
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
    abstract fun bindViewModelFactory(factory: ExpenseTrackerViewModelFactory): ViewModelProvider.Factory
}
