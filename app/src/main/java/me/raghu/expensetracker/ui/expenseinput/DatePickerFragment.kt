package me.raghu.expensetracker.ui.expenseinput

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import java.util.*

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private var model: DateShareViewModel? = null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        model = activity?.let { ViewModelProviders.of(it).get(DateShareViewModel::class.java) }
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // Create a new instance of DatePickerDialog and return it
        return DatePickerDialog(activity as AppCompatActivity, this, year, month, day)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        val c = Calendar.getInstance()
        c.set(Calendar.YEAR,year)
        c.set(Calendar.MONTH,month)
        c.set(Calendar.DAY_OF_MONTH,day)
        model?.selectedDate?.value = c.time
    }
}