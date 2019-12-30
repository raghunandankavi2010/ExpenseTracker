package me.raghu.expensetracker.ui.common

import android.util.Log
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import android.view.ViewGroup
import me.raghu.expensetracker.db.Expense
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * A generic RecyclerView adapter that uses Data Binding & DiffUtil.
 *
 * @param <T> Type of the items in the list
 * @param <V> The type of the ViewDataBinding
</V></T> */
abstract class DataBoundListAdapter<T, V : ViewDataBinding>(
    appExecutors: Executor,
    diffCallback: DiffUtil.ItemCallback<T>
) : ListAdapter<T, DataBoundViewHolder<V>>(
    AsyncDifferConfig.Builder<T>(diffCallback)
        .setBackgroundThreadExecutor(appExecutors)
        .build()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBoundViewHolder<V> {
        val binding = createBinding(parent)
        return DataBoundViewHolder(binding)
    }

    protected abstract fun createBinding(parent: ViewGroup): V

    override fun onBindViewHolder(holder: DataBoundViewHolder<V>, position: Int) {
        Log.i("Expenses List",""+itemCount)
        bind(holder.binding, getItem(position))
        holder.binding.executePendingBindings()
    }

    protected abstract fun bind(binding: V, item: T)
}
