package me.raghu.expensetracker.ui.editexpense

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class ShareDateModel : ViewModel() {
    val selectedDate = MutableLiveData<Date>()
}