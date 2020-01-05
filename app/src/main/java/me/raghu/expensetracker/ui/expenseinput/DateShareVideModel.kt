package me.raghu.expensetracker.ui.expenseinput

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class DateShareViewModel : ViewModel() {

    val selectedDate = MutableLiveData<Date>()

    init {
        selectedDate.value = Date()
    }
}