package me.raghu.expensetracker.ui.chart


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import me.raghu.chartslib.hellocharts.model.PointValue
import me.raghu.expensetracker.repository.DatabaseRepository
import me.raghu.expensetracker.utils.*
import java.util.*
import javax.inject.Inject


class LineChartViewModel @Inject constructor(private val databaseRepository: DatabaseRepository): ViewModel() {
    val date = Date()
    val expenseList = databaseRepository.getExpensesForLineChart( date.getFirstDateOfMonth(), getLastDateOfMonth())
    val liveDataLineChartValues = expenseList.switchMap {
        val chartEntry: MutableList<PointValue> = mutableListOf()
        for (expense in it) {
            if(expense.date!=null && expense.expenseAmt!=null) {
                val lineChartEntry =
                    PointValue(expense.date.getDayOfMonth(), expense.expenseAmt.toFloat())
                lineChartEntry.setLabel(expense.date.getDayofMonth())

                Log.i("Index",""+expense.expenseAmt)
                chartEntry.add(lineChartEntry)
            }
        }

        val liveData = MutableLiveData<MutableList<PointValue>>()
        liveData.value = chartEntry
        liveData
        }

    }
