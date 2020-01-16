package me.raghu.expensetracker.ui.chart


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import co.csadev.kellocharts.model.PointValue
import me.raghu.expensetracker.repository.DatabaseRepository
import me.raghu.expensetracker.utils.getDayOfMonth
import me.raghu.expensetracker.utils.getFirstDateOfMonth
import me.raghu.expensetracker.utils.getLastDateOfMonth
import timber.log.Timber
import javax.inject.Inject

class LineChartViewModel @Inject constructor(@Suppress("UNUSED_PARAMETER") private val databaseRepository: DatabaseRepository) :
    ViewModel() {

    private val expenseList =
        databaseRepository.getExpensesForLineChart(getFirstDateOfMonth(), getLastDateOfMonth())
    val liveDataLineChartValues = expenseList.switchMap {
        val chartEntry: MutableList<PointValue> = mutableListOf()
        for (expense in it) {
            if (expense.date != null && expense.expenseAmt != null) {
                val lineChartEntry =
                    PointValue(expense.date.getDayOfMonth(), expense.expenseAmt.toFloat())
                Timber.i(expense.expenseAmt)
                chartEntry.add(lineChartEntry)
            }
        }

        val liveData = MutableLiveData<MutableList<PointValue>>()
        liveData.value = chartEntry
        liveData
    }
}
