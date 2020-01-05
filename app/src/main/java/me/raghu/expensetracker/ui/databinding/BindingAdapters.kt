package me.raghu.expensetracker.ui.databinding

import android.content.Context
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import me.raghu.expensetracker.R
import me.raghu.expensetracker.utils.dateToString
import me.raghu.expensetracker.utils.toDateFormat
import java.util.*


object BindingAdapters {


    @BindingAdapter("binding")
    @JvmStatic
    fun bindEditText(
        view: EditText,
        bindableString: MutableLiveData<String>
    ) {
        var pair: Pair<MutableLiveData<String>, TextWatcherAdapter>? = null
        if (view.getTag(R.id.binded) != null) {
            pair = view.getTag(R.id.binded) as Pair<MutableLiveData<String>, TextWatcherAdapter>
        }

        if (pair == null || pair.first != bindableString) {
            if (pair != null) {
                view.removeTextChangedListener(pair.second)
            }
            val watcher: TextWatcherAdapter = object : TextWatcherAdapter() {
                override fun onTextChanged(
                    s: CharSequence,
                    arg1: Int, arg2: Int, arg3: Int
                ) {
                    bindableString.value = s.toString()
                }
            }
            view.setTag(
                R.id.binded,
                Pair(bindableString, watcher)
            )
            view.addTextChangedListener(watcher)
        }
        val newValue = bindableString.value
        if (view.text.toString() != newValue) {
            view.setText(newValue)
        }
    }

    @BindingAdapter("hideKeyboardOnInputDone")
    @JvmStatic
    fun hideKeyboardOnInputDone(view: EditText, enabled: Boolean) {
        if (!enabled) return
        val listener = TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                view.clearFocus()
                val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE)
                        as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
            false
        }
        view.setOnEditorActionListener(listener)
    }

    @BindingAdapter("amount")
    @JvmStatic
    fun setAmount(textView: TextView, total: LiveData<Float>) {
        if (total.value == null) {
            textView.text = textView.resources.getString(
                R.string.amount, 0f,
                Currency.getInstance(Locale.getDefault()).getSymbol(Locale.getDefault())
            )
        } else {
            textView.text = textView.resources.getString(
                R.string.amount, total.value,
                Currency.getInstance(Locale.getDefault()).getSymbol(Locale.getDefault())
            )
        }
    }

    @BindingAdapter(value = ["selectedValue"], requireAll = false)
    @JvmStatic
    fun setSelectedSpinnerValue(spinner: Spinner, spinnerValue: MutableLiveData<String>) {
        spinnerValue.value?.let {
            spinner.setSelection(getIndex(spinner, it))
        }
        spinner.onItemSelectedListener = object :
            OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                val value: String = parent.getItemAtPosition(position) as String
                spinnerValue.value = value
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }

    private fun getIndex(spinner: Spinner, myString: String): Int {
        var index = 0
        for (i in 0 until spinner.count) {
            if (spinner.getItemAtPosition(i) == myString) {
                index = i
            }
        }
        return index
    }

    @BindingAdapter("hideKeyboardOnButtonClick")
    @JvmStatic
    fun hideKeyboardOnButtonClick(view: MaterialButton, hideKeyBoard: MutableLiveData<Boolean>) {
        hideKeyBoard.value?.let {
            if (it) {
                val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE)
                        as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
                hideKeyBoard.value = false
            }
        }
    }

    @BindingAdapter("text")
    @JvmStatic
    fun text(textView: TextView, value: Float) {
        textView.text = textView.resources.getString(
            R.string.exceeded_expense_limit, value,
            Currency.getInstance(Locale.getDefault()).getSymbol(Locale.getDefault())
        )
    }

    @BindingAdapter("textDate")
    @JvmStatic
    fun textDate(view: TextView, bindableDate: MutableLiveData<Date>) {
        Log.i("Date",""+bindableDate.value?.dateToString())
        view.text = bindableDate.value?.dateToString()
    }

    @BindingAdapter("textDateEdit")
    @JvmStatic
    fun textDateEdit(view: TextView, bindableDate: MutableLiveData<Date>) {
        Log.i("Date",""+bindableDate.value?.dateToString())
        view.text = bindableDate.value?.dateToString()
    }
}


