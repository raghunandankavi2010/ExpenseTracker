package me.raghu.expensetracker.ui.editexpense

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.raghu.expensetracker.repository.DatabaseRepository
import java.util.*
import javax.inject.Inject

class ExpenseEditViewModel @Inject constructor(private val databaseRepository: DatabaseRepository) :
    ViewModel() {

    var expenseType = MutableLiveData<String>()

    var id = MutableLiveData<Int>()

    var amount = MutableLiveData<String>()

    var amountError = MutableLiveData<String>()

    var remarks = MutableLiveData<String>()

    var remarksError = MutableLiveData<String>()

    var editedSuccessFully = MutableLiveData<Boolean>(false)

    var hideKeyBoard = MutableLiveData<Boolean>(false)

    private var isAmtValidated = false
    private var isRemarksValidated = false


    fun updateExpenseToDb() {
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
            viewModelScope.launch {
                with(databaseRepository) {
                    updateExpense(
                        id.value!!,
                        expenseType.value!!,
                        amount.value!!,
                        remarks.value!!,
                        date
                    )
                }
                editedSuccessFully.value = true
                reset()
            }
        }
    }


    private fun reset() {

        expenseType.value = ""
        amount.value = ""
        remarks.value = ""
    }
}