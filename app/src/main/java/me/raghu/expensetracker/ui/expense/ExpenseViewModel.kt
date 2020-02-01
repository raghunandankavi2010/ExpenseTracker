package me.raghu.expensetracker.ui.expense

import androidx.lifecycle.*
import androidx.paging.Config
import androidx.paging.toLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.raghu.expensetracker.db.ExpenseDao
import me.raghu.expensetracker.repository.DatabaseRepository
import java.util.*
import javax.inject.Inject

class ExpenseViewModel
@Inject constructor(
    private val databaseRepository: DatabaseRepository,
    expenseDao: ExpenseDao
) : ViewModel() {

    private val range: MutableLiveData<Range> = MutableLiveData()

    val expenseExceeded: MutableLiveData<Float> = MutableLiveData(0f)

    val totalExpenseCurrentMonth = range.switchMap { range ->
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            emitSource(
                range.ifExists { startDate, endDate ->
                    databaseRepository.getExpensesForCurrentMonth(
                        startDate,
                        endDate
                    )
                }
            )
        }
    }

    val expense = expenseDao.fetchExpenses().toLiveData(
        Config(pageSize = 30, enablePlaceholders = true)
    )

    data class Range(val startDate: Date, val endDate: Date) {
        fun <T> ifExists(f: (Date, Date) -> LiveData<T>): LiveData<T> {
            return f(startDate, endDate)
        }
    }

    fun setDateRange(startDate: Date, endDate: Date) {
        val update = Range(
            startDate,
            endDate
        )
        if (range.value == update) {
            return
        }
        range.value = update
    }

    fun deleteExpense(id: Int) {
        viewModelScope.launch {
            databaseRepository.deleteExpense(id)
        }
    }
}

