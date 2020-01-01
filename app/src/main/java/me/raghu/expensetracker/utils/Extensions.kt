package me.raghu.expensetracker.utils

import android.util.Log
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


    fun getLastDateOfMonth(): Date {
        val cal: Calendar = Calendar.getInstance()
        val lastDate: Int = cal.getActualMaximum(Calendar.DATE)
        cal.set(Calendar.DAY_OF_MONTH, lastDate)
        cal.set(Calendar.HOUR_OF_DAY,0)
        Log.i("last day",""+cal.time.dateToString()+""+lastDate)
        return cal.time
    }

    fun Date.getFirstDateOfMonth(): Date {
        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY,0)
        cal[Calendar.DAY_OF_MONTH] = cal.getActualMinimum(Calendar.DAY_OF_MONTH)
        Log.i("first day",""+cal.time.dateToString())
        return cal.time
    }

    fun Date.dateToString(): String {
        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss a", Locale.getDefault())
        return dateFormat.format(this)
    }

