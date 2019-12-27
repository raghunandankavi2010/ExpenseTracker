package me.raghu.expensetracker.ui

import android.text.TextUtils
import android.util.Log
import androidx.databinding.Observable
import androidx.databinding.Observable.OnPropertyChangedCallback
import androidx.lifecycle.ViewModel
import me.raghu.expensetracker.db.Expense
import javax.inject.Inject


class ExpenseInputViewModel
@Inject constructor() : ViewModel() {

    @JvmField
    var expenseType = BindableString()
    @JvmField
    var expenseTypeError = BindableString()
    @JvmField
    var amount = BindableString()
    @JvmField
    var amountError = BindableString()
    @JvmField
    var remarks = BindableString()
    @JvmField
    var remarksError = BindableString()

    init {
        val callback: OnPropertyChangedCallback = object : OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable, propertyId: Int) {
                when {
                    sender === expenseType -> {
                        expenseTypeError.set("")
                    }
                    sender == amount -> {
                        expenseTypeError.set("")
                    }
                    sender === remarks -> {
                        remarksError.set("")
                    }
                }
            }
        }
        expenseType.addOnPropertyChangedCallback(callback)
        amount.addOnPropertyChangedCallback(callback)
        amount.addOnPropertyChangedCallback(callback)
    }

    fun performValidation() {
        if (expenseType.isEmpty) {
            expenseTypeError.set("Expense Type cannot be empty")
        } else {
            expenseTypeError.set("")
        }
        if (amount.isEmpty || amount.get() == "0") {
            amountError.set("Amount cannot be empty or 0")
        } else {
            amountError.set("")
        }
        if (remarks.isEmpty) {
            remarksError.set("Remarks cannot be empty")
        } else {
            remarksError.set("")
        }
        if (!expenseType.isEmpty && !amount.isEmpty && !remarks.isEmpty) {
            val expense = Expense(
                expenseType = expenseType.get(),
                expenseAmt = amount.get(),
                remarks = remarks.get()
            )
            addExpenseToDb(expense)
        }
    }

    private fun addExpenseToDb(expense: Expense) {
        Log.i("Expense Amount", "" + expense.expenseAmt)
    }

}
