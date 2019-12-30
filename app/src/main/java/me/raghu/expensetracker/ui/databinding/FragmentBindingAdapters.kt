
package me.raghu.expensetracker.ui.databinding


import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import me.raghu.expensetracker.R
import me.raghu.expensetracker.utils.dateToString
import java.util.*
import javax.inject.Inject

/**
 * Binding adapters that work with a fragment instance.
 */

class FragmentBindingAdapters @Inject constructor(val fragment: Fragment) {

    @BindingAdapter("date")
     fun  bindDate(textView: TextView,date: Date) {
       textView.text = date.dateToString()
    }

}

