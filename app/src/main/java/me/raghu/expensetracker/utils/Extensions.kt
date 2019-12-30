package me.raghu.expensetracker.utils

import java.util.*


 fun getLastDateOfMonth(): Date {
    val cal: Calendar = Calendar.getInstance()
    val lastDate: Int = cal.getActualMaximum(Calendar.DATE)
    cal.set(Calendar.DAY_OF_MONTH, lastDate)
    return cal.time
}

 fun Date.getFirstDateOfMonth(): Date {
    val cal = Calendar.getInstance()
    cal.time = this
    cal[Calendar.DAY_OF_MONTH] = cal.getActualMinimum(Calendar.DAY_OF_MONTH)
    return cal.time
}