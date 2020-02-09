
package me.raghu.expensetracker.ui.expense

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import me.raghu.expensetracker.R
import me.raghu.expensetracker.databinding.ExpenseItemBinding
import me.raghu.expensetracker.db.Expense
import me.raghu.expensetracker.ui.common.DataBoundListAdapter
import me.raghu.expensetracker.utils.ExpenseEvent
import java.util.concurrent.Executor

/**
 * A RecyclerView adapter for [ExpenseFragment] class.
 */
class ExpenseAdapter(
    private val dataBindingComponent: DataBindingComponent,
    appExecutors : Executor,
    private val expenseClickCallback: ((ExpenseEvent) -> Unit)?
) : DataBoundListAdapter<Expense, ExpenseItemBinding>(appExecutors = appExecutors,

    diffCallback = object : DiffUtil.ItemCallback<Expense>() {
        override fun areItemsTheSame(oldItem: Expense, newItem: Expense): Boolean {
            return oldItem.id == newItem.id && oldItem.expenseType == newItem.expenseType
                    && oldItem.expenseAmt == newItem.expenseAmt && oldItem.date == newItem.date
        }

        override fun areContentsTheSame(oldItem: Expense, newItem: Expense): Boolean {
            return oldItem.id == newItem.id && oldItem.expenseType == newItem.expenseType
                    && oldItem.expenseAmt == newItem.expenseAmt && oldItem.date == newItem.date
        }
    }
) {

    override fun createBinding(parent: ViewGroup): ExpenseItemBinding {
        val binding = DataBindingUtil.inflate<ExpenseItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.expense_item,
            parent,
            false,
            dataBindingComponent
        )
        binding.delete.setOnClickListener {
            binding.expense?.let {
                expenseClickCallback?.invoke(ExpenseEvent("delete",it))
            }
        }
        binding.edit.setOnClickListener {
            binding.expense?.let {
                expenseClickCallback?.invoke(ExpenseEvent("edit",it))
            }
        }
        return binding
    }

    override fun bind(binding: ExpenseItemBinding, item: Expense) {
        binding.expense = item
    }
}
