package me.raghu.expensetracker.ui

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.raghu.expensetracker.db.Expense
import javax.inject.Inject


class ExpenseInputViewModel
@Inject constructor() : ViewModel() {

    var expenseType = MutableLiveData<String>()

    var expenseTypeError = MutableLiveData<String>()

    var amount = MutableLiveData<String>()

    var amountError = MutableLiveData<String>()

    var remarks = MutableLiveData<String>()

    var remarksError = MutableLiveData<String>()

    private var isTypeValidated = false
    private var isAmtValidated = false
    private var isRemarksValidated = false

    fun performValidation() {

        if (TextUtils.isEmpty(expenseType.value?.trim())) {
            expenseTypeError.value = "Expense Type cannot be empty"
        } else {
            expenseTypeError.value = ""
            isTypeValidated = true
        }
        if (TextUtils.isEmpty(amount.value?.trim()) || amount.value?.trim() == "0") {
            amountError.value = "Amount cannot be empty or 0"
        } else {
            amountError.value = ""
            isAmtValidated = true
        }
        if (TextUtils.isEmpty(remarks.value?.trim())) {
            remarksError.value = "Remarks cannot be empty"
        } else {
            remarksError.value = ""
            isRemarksValidated = true
        }
        if (isTypeValidated && isAmtValidated && isRemarksValidated) {
            val expense = Expense(
                expenseType = expenseType.value,
                expenseAmt = amount.value,
                remarks = remarks.value
            )
            addExpenseToDb(expense)
        }
    }

    private fun addExpenseToDb(expense: Expense) {

        Log.i("Expense Amount", "" + expense.expenseAmt)
    }

}
