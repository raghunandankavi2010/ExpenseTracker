
package me.raghu.expensetracker.ui.expense

import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import me.raghu.expensetracker.R
import me.raghu.expensetracker.databinding.ExpenseItemBinding
import me.raghu.expensetracker.db.Expense
import me.raghu.expensetracker.ui.common.DataBoundListAdapter
import me.raghu.expensetracker.utils.dateToString

/**
 * A RecyclerView adapter for [Repo] class.
 */
class ExpenseAdapter(
    private val dataBindingComponent: DataBindingComponent,
    private val repoClickCallback: ((Expense) -> Unit)?
) : DataBoundListAdapter<Expense, ExpenseItemBinding>(

    diffCallback = object : DiffUtil.ItemCallback<Expense>() {
        override fun areItemsTheSame(oldItem: Expense, newItem: Expense): Boolean {
            return oldItem.expenseAmt == newItem.expenseAmt
                    && oldItem.expenseType == newItem.expenseType && oldItem.remarks == newItem.remarks
                    && oldItem.date?.dateToString() == newItem.date?.dateToString()
        }

        override fun areContentsTheSame(oldItem: Expense, newItem: Expense): Boolean {
            return oldItem.expenseAmt == newItem.expenseAmt
                    && oldItem.expenseType == newItem.expenseType && oldItem.remarks == newItem.remarks
                    && oldItem.date?.dateToString() == newItem.date?.dateToString()
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
        binding.root.setOnClickListener {
            binding.expense?.let {
                repoClickCallback?.invoke(it)
            }
        }
        return binding
    }

    override fun bind(binding: ExpenseItemBinding, item: Expense) {
        binding.expense = item
    }
}
