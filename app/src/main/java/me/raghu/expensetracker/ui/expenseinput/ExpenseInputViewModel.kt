package me.raghu.expensetracker.ui.expenseinput

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.raghu.expensetracker.db.Expense
import me.raghu.expensetracker.repository.DatabaseRepository
import java.util.*
import javax.inject.Inject


class ExpenseInputViewModel
@Inject constructor(private val databaseRepository: DatabaseRepository) : ViewModel() {

    var expenseType = MutableLiveData<String>()

    var amount = MutableLiveData<String>()

    var amountError = MutableLiveData<String>()

    var remarks = MutableLiveData<String>()

    var remarksError = MutableLiveData<String>()

    var insertedSuccessFully = MutableLiveData<Boolean>(false)

    var hideKeyBoard = MutableLiveData<Boolean>(false)

    private var isAmtValidated = false
    private var isRemarksValidated = false

    fun performValidation() {

        hideKeyBoard.value = true
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
        if (isAmtValidated && isRemarksValidated) {
            val date = Calendar.getInstance().time
            val expense = Expense(
                expenseType = expenseType.value,
                expenseAmt = amount.value,
                remarks = remarks.value,
                date = date
            )
            addExpenseToDb(expense)
        }
    }

    private fun addExpenseToDb(expense: Expense) {
        Log.i("Value",""+expense.date.toString())
        viewModelScope.launch {
            val long = databaseRepository.insert(expense)
            Log.i("Value",""+long)
            insertedSuccessFully.value = true
            reset()
        }
    }

    private fun reset(){

        expenseType.value = "Cash"
        amount.value = ""
        remarks.value = ""
    }

}
