package me.raghu.expensetracker.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.raghu.expensetracker.ui.chart.LineChartFragment
import me.raghu.expensetracker.ui.editexpense.EditExpenseFragment
import me.raghu.expensetracker.ui.expense.ExpenseFragment
import me.raghu.expensetracker.ui.expenseinput.ExpenseInput

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeExpenseInputFragment(): ExpenseInput

    @ContributesAndroidInjector
    abstract fun contributeExpenseFragment(): ExpenseFragment

    @ContributesAndroidInjector
    abstract fun contributeEditExpenseFragment(): EditExpenseFragment

    @ContributesAndroidInjector
    abstract fun contributeLineChartFragment(): LineChartFragment

}
