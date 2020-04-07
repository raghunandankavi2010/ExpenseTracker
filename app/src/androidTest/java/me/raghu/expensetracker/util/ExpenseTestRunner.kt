package me.raghu.expensetracker.util

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

import me.raghu.expensetracker.TestApp

/**
 * Custom runner to disable dependency injection.
 */
class ExpenseTestRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
        return super.newApplication(cl, TestApp::class.java.name, context)
    }
}
