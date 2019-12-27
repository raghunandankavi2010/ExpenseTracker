package me.raghu.expensetracker.ui

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.ViewModel
import me.raghu.expensetracker.db.Expense
import javax.inject.Inject
import androidx.lifecycle.MutableLiveData


class ExpenseInputViewModel
@Inject constructor() : ViewModel() {

    var expenseType = MutableLiveData<String>()

    var expenseTypeError =  MutableLiveData<String>()

    var amount =  MutableLiveData<String>()

    var amountError =  MutableLiveData<String>()

    var remarks =  MutableLiveData<String>()

    var remarksError =  MutableLiveData<String>()

    fun performValidation() {

      if (TextUtils.isEmpty(expenseType.value)) {
            expenseTypeError.value = "Expense Type cannot be empty"
        } else {
            expenseTypeError.value = ""
        }
        if (TextUtils.isEmpty(amount.value)|| amount.value=="0") {
            amountError.value = "Amount cannot be empty or 0"
        } else {
            amountError.value = ""
        }
        if (TextUtils.isEmpty(remarks.value)) {
            remarksError.value = "Remarks cannot be empty"
        } else {
            remarksError.value = ""
        }
        if (!TextUtils.isEmpty(expenseType.value) && !TextUtils.isEmpty(amount.value) && !TextUtils.isEmpty(remarks.value)) {
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
