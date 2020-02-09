package me.raghu.expensetracker.utils

import me.raghu.expensetracker.db.Expense

/**
 *  A model class for particular expense. Currently not used for anything
 */

data class ExpenseEvent(var type: String,val expense: Expense)

