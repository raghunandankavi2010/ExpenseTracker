package me.raghu.expensetracker
import android.app.Application

/**
 * We use a separate App for tests to prevent initializing dependency injection.
 *
 * See [me.raghu.expensetracker.util.ExpenseTestRunner].
 */
class TestApp : Application()
