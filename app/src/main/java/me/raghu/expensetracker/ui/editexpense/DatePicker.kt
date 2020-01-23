package me.raghu.expensetracker.ui.editexpense

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider

import java.util.*

class DatePicker : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private var model: ShareDateModel? = null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        model = activity?.let { ViewModelProvider(it).get(ShareDateModel::class.java) }
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // Create a new instance of DatePickerDialog and return it
        return DatePickerDialog(activity as AppCompatActivity, this, year, month, day)
    }

    override fun onDateSet(view: android.widget.DatePicker?, year: Int, month: Int, day: Int) {
        val c = Calendar.getInstance()
        c.set(Calendar.YEAR,year)
        c.set(Calendar.MONTH,month)
        c.set(Calendar.DAY_OF_MONTH,day)
        model?.selectedDate?.value = c.time
    }
}