package me.raghu.expensetracker.utils

import android.util.Log
import java.text.NumberFormat
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
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormat.format(this)
    }

   fun Float.toDefaultFormat(): String {
    return NumberFormat.getCurrencyInstance(Locale.getDefault()).format(this)
   }

   fun String.toDateFormat(): Date? {
       return SimpleDateFormat("dd/MM/yyyy",Locale.getDefault()).parse(this)
   }
