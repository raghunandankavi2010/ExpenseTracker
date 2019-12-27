
package me.raghu.expensetracker.ui

import android.content.Context
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion
import me.raghu.expensetracker.R


object BindingAdapters {

    @BindingConversion
    @JvmStatic
    fun convertBindableToString(
        bindableString: BindableString): String {
        return bindableString.get()
    }


    @BindingAdapter("binding")
    @JvmStatic
    fun bindEditText(view: EditText,
                     bindableString: BindableString) {
        var pair: Pair<BindableString, TextWatcherAdapter>? = null
        if(view.getTag(R.id.binded)!=null){
            pair= view.getTag(R.id.binded) as Pair<BindableString, TextWatcherAdapter>
        }

        if (pair == null || pair.first != bindableString) {
            if (pair != null) {
                view.removeTextChangedListener(pair.second)
            }
            val watcher: TextWatcherAdapter = object : TextWatcherAdapter() {
                override fun onTextChanged(s: CharSequence,
                                           start: Int, before: Int, count: Int) {
                    bindableString.set(s.toString())
                }
            }
            view.setTag(R.id.binded,
                Pair(bindableString, watcher))
            view.addTextChangedListener(watcher)
        }
        val newValue = bindableString.get()
        if (view.text.toString() != newValue) {
            view.setText(newValue)
        }
    }

    @BindingAdapter("hideKeyboardOnInputDone")
    @JvmStatic fun hideKeyboardOnInputDone(view: EditText, enabled: Boolean) {
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
}
