package me.raghu.expensetracker.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.raghu.expensetracker.db.Expense
import javax.inject.Inject


class ExpenseInputViewModel
@Inject constructor(): ViewModel() {

    val expenseType = MutableLiveData<String>("")
    val expenseAmount = MutableLiveData<String>("")
    val expenseRemark = MutableLiveData<String>("")


}
