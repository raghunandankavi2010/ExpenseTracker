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


    fun resetError() {
        expenseTypeError.set("")
        amountError.set("")
        remarksError.set("")
    }


    fun performValidation() {
        if (TextUtils.isEmpty(expenseType.get())) {
            expenseTypeError.set("Expense Type cannot be empty")
        } else {
            expenseTypeError.set("")
        }
        if (TextUtils.isEmpty(amount.get()) || amount.get() == "0") {
            amountError.set("Amount cannot be empty or 0")
        } else {
            amountError.set("")
        }
        if (TextUtils.isEmpty(remarks.get())) {
            remarksError.set("Remarks cannot be empty")
        } else {
            remarksError.set("")
        }
        if (!TextUtils.isEmpty(expenseType.get()) && !TextUtils.isEmpty(amount.get()) && !TextUtils.isEmpty(remarks.get())) {
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
