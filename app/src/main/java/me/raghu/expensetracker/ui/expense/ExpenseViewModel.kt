package me.raghu.expensetracker.ui.expense

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import me.raghu.expensetracker.db.Expense
import me.raghu.expensetracker.repository.DatabaseRepository
import java.util.*
import javax.inject.Inject

class ExpenseViewModel
@Inject constructor(private val databaseRepository: DatabaseRepository) : ViewModel() {

    private val range: MutableLiveData<Range> = MutableLiveData()


    val toatalExpenseCurrentMonth = range.switchMap { range ->
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            emit(databaseRepository.getExpensesForCurrentMonth(range.startDate, range.endDate))
        }
    }

    val expenses: LiveData<List<Expense>> = liveData {
        val data = databaseRepository.getExpenses()
        emit(data)
    }

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
}

