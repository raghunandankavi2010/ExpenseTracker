/*
 * Copyright 2019 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.raghu.expensetracker.ui.databinding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import me.raghu.expensetracker.R
import me.raghu.expensetracker.utils.dateToString
import java.util.*
import javax.inject.Inject

/**
 * Binding adapters that work with a fragment instance.
 */

class FragmentBindingAdapters @Inject constructor(val fragment: Fragment) {

    @BindingAdapter("date")
     fun bindDate(textView: TextView,date: Date) {
       textView.text = date.dateToString()
    }

    @BindingAdapter("amountspent")
    fun bindAmount(textView: TextView, amount: String) {
        textView.text = textView.resources.getString(R.string.expense_amount, amount,
            Currency.getInstance(Locale.getDefault()).getSymbol(Locale.getDefault()))
    }

}

