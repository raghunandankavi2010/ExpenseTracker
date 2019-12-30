package me.raghu.expensetracker.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.raghu.expensetracker.ui.ExpenseFragment
import me.raghu.expensetracker.ui.expenseinput.ExpenseInput

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeExpenseInputFragment(): ExpenseInput

    @ContributesAndroidInjector
    abstract fun contributeExpenseFragment(): ExpenseFragment

}
