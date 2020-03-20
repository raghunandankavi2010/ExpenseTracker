package me.raghu.expensetracker.ui.expenseinput

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.raghu.expensetracker.db.Expense
import me.raghu.expensetracker.repository.DatabaseRepository
import timber.log.Timber
import java.util.*
import javax.inject.Inject


class ExpenseInputViewModel
@Inject constructor(private val databaseRepository: DatabaseRepository) : ViewModel() {

    val expenseType = MutableLiveData<String>()

    val amount = MutableLiveData<String>()

    var amountError = MutableLiveData<String>()

    val remarks = MutableLiveData<String>()

    val remarksError = MutableLiveData<String>()

    val insertedSuccessFully = MutableLiveData<Boolean>(false)

    val hideKeyBoard = MutableLiveData<Boolean>(false)

    var selectedDate = MutableLiveData<Date>()

    private var isAmtValidated = false
    private var isRemarksValidated = false

    fun performValidation() {

        hideKeyBoard.value = true
        if (TextUtils.isEmpty(amount.value?.trim()) || amount.value?.trim() == "0") {
            amountError.value = "Amount cannot be empty or 0"
            isAmtValidated = false
        } else {
            amountError.value = ""
            isAmtValidated = true
        }
        if (TextUtils.isEmpty(remarks.value?.trim())) {
            remarksError.value = "Remarks cannot be empty"
            isRemarksValidated = false
        } else {
            remarksError.value = ""
            isRemarksValidated = true
        }
        if (isAmtValidated && isRemarksValidated) {
            val date = selectedDate.value
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
        Timber.i(expense.date.toString())
        viewModelScope.launch {
            val long = databaseRepository.insert(expense)
            Timber.i(long.toString())
            insertedSuccessFully.value = true
            reset()
        }
    }

    private fun reset() {
        expenseType.value = "Cash"
        amount.value = ""
        remarks.value = ""
    }
}
