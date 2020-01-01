package me.raghu.expensetracker.utils

import me.raghu.expensetracker.db.Expense

class ExpenseEvent(var type: String,val expense: Expense) {

}